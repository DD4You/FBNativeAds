package in.dd4you.fbnativeads;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.ads.AudienceNetworkAds;

import java.util.ArrayList;
import java.util.List;

import in.dd4you.fbnativeads.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AudienceNetworkAds.initialize(this);


        List<Object> modelList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            modelList.add(new MainModel("DD4You.in", "Please Subscribe my Channel " + i));
        }
        MainAdapter adapter = new MainAdapter(this, modelList, modelList.size());
        binding.recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.initNativeAds();
    }
}