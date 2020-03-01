package com.erunetimeterror.wai.fragments;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.erunetimeterror.wai.MainApplication;
import com.erunetimeterror.wai.R;
import com.erunetimeterror.wai.utils.PlacesAdapter;
import com.erunetimeterror.wai.utils.Statics;
import com.erunetimeterror.wai.utils.Tools;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPhotoResponse;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import io.opencensus.common.ServerStatsFieldEnums;

public class MapsFragments extends Fragment implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap mMap;
    Button btnLocation;
    private double wayLatitude = 0.0, wayLongitude = 0.0;
    MarkerOptions userMOptions;
    Marker user = null;
    Bitmap userBitmap;
    PlacesAdapter adapter;
    LinearLayout fog;
    SpinKitView spin;
    String[] hobbiesArray;
    // Create the gradient.
    int[] colors = {
            Color.rgb(50, 168, 119), // green
            Color.rgb(4, 46, 28)    // red
    };

    float[] startPoints = {
            0.2f, 1f
    };

    Gradient gradient = new Gradient(colors, startPoints);

    HeatmapTileProvider mProvider;
    private TileOverlay mOverlay;
    boolean end = true;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps,container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mapView = view.findViewById(R.id.map);
        btnLocation = view.findViewById(R.id.btLoc);
        fog = view.findViewById(R.id.fog);
        spin = view.findViewById(R.id.spin_kit);


//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("places_boardgames/location");
//        GeoFire geoFire = new GeoFire(ref);
//        geoFire.setLocation("p2", new GeoLocation(50.06686043, 19.9568367));
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        getContext().registerReceiver(locationReceiver, new IntentFilter(Statics.LOCATION));

        userBitmap = Tools.getBitmap(getContext(),R.drawable.mappin);
        userBitmap = Bitmap.createScaledBitmap(userBitmap, 80, 80, false);
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(wayLatitude, wayLongitude), 15.0f));
            }
        });
        SharedPreferences prefs = getContext().getSharedPreferences(Statics.WAI_Prefs, 0);
        String tmpHobbies = prefs.getString(Statics.HOBBIES,"");
        hobbiesArray = tmpHobbies.split(";");
    }
    BroadcastReceiver locationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Statics.LOCATION)){
                wayLatitude = intent.getDoubleExtra("latitude",0);
                wayLongitude = intent.getDoubleExtra("longitude",0);
                setPosition(wayLatitude,wayLongitude);
            }
        }
    };
    private ArrayList<LatLng> readItems(int resource) throws JSONException {
        ArrayList<LatLng> list = new ArrayList<LatLng>();
        InputStream inputStream = getResources().openRawResource(resource);
        String json = new Scanner(inputStream).useDelimiter("\\A").next();
        JSONArray array = new JSONArray(json);
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            double lat = object.getDouble("lat");
            double lng = object.getDouble("lng");
            list.add(new LatLng(lat, lng));
        }
        return list;
    }
    private void addHeatMap() {
        List<LatLng> list = null;

        // Get the data: latitude/longitude positions of police stations.
        try {
            list = readItems(R.raw.inep_places);
            mProvider = new HeatmapTileProvider.Builder()
                    .data(list)
                    .gradient(gradient)
                    .build();
            mOverlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
        } catch (JSONException e) {
            Toast.makeText(getContext(), "Problem reading list of locations.", Toast.LENGTH_LONG).show();
        }



    }

    private void setPosition(double wayLatitude, double wayLongitude){
        LatLng userPos = new LatLng(wayLatitude, wayLongitude);
        if (user == null){
            userMOptions = new MarkerOptions().position(userPos).icon(BitmapDescriptorFactory.fromBitmap(userBitmap));
            user = mMap.addMarker(userMOptions);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(wayLatitude, wayLongitude), 15.0f));
        }else {
            user.setPosition(userPos);
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
//        googleMap.setMapStyle(
//                MapStyleOptions.loadRawResourceStyle(
//                        getContext(), R.raw.map_style_json));
        mMap = googleMap;
        MainApplication app = (MainApplication) getContext().getApplicationContext();

        wayLatitude = app.latitude;
        wayLongitude = app.longtitude;
        setPosition(wayLatitude,wayLongitude);
        addHeatMap();
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                List<LatLng> list = null;
                try {
                    list = readItems(R.raw.inep_places);
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Problem reading list of locations.", Toast.LENGTH_LONG).show();
                }
                for (LatLng loc : list){
                    float[] dist = new float[1];

                    Location.distanceBetween(loc.latitude,loc.longitude,latLng.latitude,latLng.longitude,dist);

                    if(dist[0]/1000 < 0.03){
                        fog.setVisibility(View.VISIBLE);
                        spin.setVisibility(View.VISIBLE);
                        Log.d("Location",loc.toString());
                        final ArrayList<String> placesId = new ArrayList<>();
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("places_boardgames/location");
                        GeoFire geoFire = new GeoFire(ref);
                        final boolean[] first = {true};
                        Places.initialize(getContext().getApplicationContext(), getContext().getResources().getString(R.string.places_key));
                        PlacesClient placesClient = Places.createClient(getContext());
                        GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(loc.latitude, latLng.longitude), 0.3);

                        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
                            @Override
                            public void onKeyEntered(final String key, GeoLocation location) {
                                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference ref = database.getReference("places_boardgames/places_id");
                                ref.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        HashMap<String,String> value =(HashMap<String,String>) dataSnapshot.getValue();
                                        if (value.containsKey(key)){
                                            Log.d("PLACES", "onDataChange: "+ key);
                                            placesId.add(value.get(key));
                                            if (first[0]){
                                                createPlacesDialog(placesId);
                                                first[0] = false;
                                            }else {
                                                addToAdapter(value.get(key));
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        fog.setVisibility(View.GONE);
                                        spin.setVisibility(View.GONE);
                                    }
                                });

                            }
                            @Override
                            public void onKeyExited(String key) {
                            }@Override
                            public void onKeyMoved(String key, GeoLocation location) { }
                            @Override
                            public void onGeoQueryReady() {
                            }
                            @Override
                            public void onGeoQueryError(DatabaseError error) { }
                        });

                        break;
                    }
                }
            }
        });
    }
    public void addToAdapter(String placeId){
        final PlacesClient placesClient = Places.createClient(getContext());
        List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.WEBSITE_URI, Place.Field.PHOTO_METADATAS);
        FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeId, placeFields);
        final Place[] place = new Place[1];
        final String[] attribute = new String[1];
        final Bitmap[] bitmap = new Bitmap[1];
        placesClient.fetchPlace(request).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
            @Override
            public void onSuccess(FetchPlaceResponse fetchPlaceResponse) {
                place[0] = fetchPlaceResponse.getPlace();

                PhotoMetadata photoMetadata = fetchPlaceResponse.getPlace().getPhotoMetadatas().get(0);
                attribute[0] = photoMetadata.getAttributions();
                FetchPhotoRequest photoRequest = FetchPhotoRequest.builder(photoMetadata)
                        //                           .setMaxWidth(200) // Optional.
                        //                           .setMaxHeight(200) // Optional.
                        .build();
                placesClient.fetchPhoto(photoRequest).addOnSuccessListener(new OnSuccessListener<FetchPhotoResponse>() {
                    @Override
                    public void onSuccess(FetchPhotoResponse fetchPhotoResponse) {
                        bitmap[0] = fetchPhotoResponse.getBitmap();
                        while (true){
                            if (end){
                                adapter.addItem(place[0], attribute[0], bitmap[0], adapter.getItemCount());
                                break;
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        fog.setVisibility(View.GONE);
                        spin.setVisibility(View.GONE);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                fog.setVisibility(View.GONE);
                spin.setVisibility(View.GONE);
            }
        });
    }
    public void createPlacesDialog(ArrayList<String> placesIds){
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_places);
        final ArrayList<Place> placeList = new ArrayList<>();
        final ArrayList<String> attributes = new ArrayList<>();
        final ArrayList<Bitmap> bitmaps = new ArrayList<>();
        Places.initialize(getContext().getApplicationContext(), getContext().getResources().getString(R.string.places_key));
        final PlacesClient placesClient = Places.createClient(getContext());
        for (String id : placesIds){
            // Specify the fields to return.
            List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.WEBSITE_URI, Place.Field.PHOTO_METADATAS);
            FetchPlaceRequest request = FetchPlaceRequest.newInstance(id, placeFields);
            final RecyclerView recyclerView = dialog.findViewById(R.id.recyclerPlaces);
            recyclerView.setLayoutManager(new LinearLayoutManager(dialog.getContext(),LinearLayoutManager.HORIZONTAL, false));
            placesClient.fetchPlace(request).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
                @Override
                public void onSuccess(FetchPlaceResponse fetchPlaceResponse) {
                    placeList.add(fetchPlaceResponse.getPlace());

                    PhotoMetadata photoMetadata = fetchPlaceResponse.getPlace().getPhotoMetadatas().get(0);
                    attributes.add(photoMetadata.getAttributions());
                    FetchPhotoRequest photoRequest = FetchPhotoRequest.builder(photoMetadata)
 //                           .setMaxWidth(200) // Optional.
 //                           .setMaxHeight(200) // Optional.
                            .build();
                    placesClient.fetchPhoto(photoRequest).addOnSuccessListener(new OnSuccessListener<FetchPhotoResponse>() {
                        @Override
                        public void onSuccess(FetchPhotoResponse fetchPhotoResponse) {
                            bitmaps.add(fetchPhotoResponse.getBitmap());
                            adapter = new PlacesAdapter(placeList, attributes, bitmaps);
                            recyclerView.setAdapter(adapter);
                            dialog.show();
                            fog.setVisibility(View.GONE);
                            spin.setVisibility(View.GONE);
                            end = true;
                        }
                }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            fog.setVisibility(View.GONE);
                            spin.setVisibility(View.GONE);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    fog.setVisibility(View.GONE);
                    spin.setVisibility(View.GONE);
                }
            });

        }



    }
}
