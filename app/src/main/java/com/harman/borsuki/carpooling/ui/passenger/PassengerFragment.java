package com.harman.borsuki.carpooling.ui.passenger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.harman.borsuki.carpooling.R;
import com.harman.borsuki.carpooling.RouteDetailActivity;
import com.harman.borsuki.carpooling.adapter.BorsukiRouteAdapter;
import com.harman.borsuki.carpooling.models.BorsukiRoute;
import com.harman.borsuki.carpooling.services.ApiClient;
import com.harman.borsuki.carpooling.services.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PassengerFragment extends Fragment {

    private ListView listView;
    private List<BorsukiRoute> list;
    private Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_passenger, container, false);
        listView = root.findViewById(R.id.lv_routes);
        context = root.getContext();
        initialize();
        return root;
    }

    private void initialize(){

        listView.setOnItemClickListener((parent, view, position, id) -> {
            BorsukiRoute clickedItem = (BorsukiRoute) listView.getItemAtPosition(position);
            Intent intent = new Intent(this.getContext(), RouteDetailActivity.class);
            Gson gson = new Gson();
            String temp;
            try {
                temp = gson.toJson(clickedItem);
            }catch (AndroidRuntimeException e) {
                e.printStackTrace();
                temp = "";
            }

            intent.putExtra("temp", temp);
            startActivity(intent);
        });

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<BorsukiRoute>> call = apiInterface.getAllRoutes();
        call.enqueue(new Callback<List<BorsukiRoute>>() {
            @Override
            public void onResponse(Call<List<BorsukiRoute>> call, Response<List<BorsukiRoute>> response) {
                if(response.isSuccessful()){
                    list = response.body();
                    listView.setAdapter(new BorsukiRouteAdapter(context, list));
                }
            }

            @Override
            public void onFailure(Call<List<BorsukiRoute>> call, Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });

    }
}