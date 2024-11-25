package com.example.movieonline_api;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

//import com.example.movieonline_api.model.VideoAdapter;
import com.example.movieonline_api.Fragment.SearchFragment;
import com.example.movieonline_api.Fragment.hisFragment;
import com.example.movieonline_api.adapter.PagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MovieActivity extends AppCompatActivity {
    private TabLayout tablayout;
    private ViewPager2 viewPager2;

    private androidx.appcompat.widget.SearchView searchBar;
    private PagerAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        initTablayout();
        SearchFragment sreach = new SearchFragment();
        PagerAdapter adapter = new PagerAdapter(this);
        viewPager2.setAdapter(adapter);

//        Intent intent = getIntent();
//        int userId = intent.getIntExtra("USER_ID", -1); // -1 là giá trị mặc định nếu không tìm thấy
//
//        if (userId != -1) {
//            // Nếu user_id được truyền, in ra log hoặc hiển thị
//            Toast.makeText(this, "User ID: " + userId, Toast.LENGTH_SHORT).show();
//            Log.d("MovieActivity", "User ID: " + userId);
//
//            // Lưu user_id để sử dụng trong các API khác
//        } else {
//            // Nếu không có user_id, hiển thị thông báo lỗi
//            Toast.makeText(this, "Không thể lấy User ID!", Toast.LENGTH_SHORT).show();
//            Log.e("MovieActivity", "User ID không tồn tại!");
//        }

        // Liên kết TabLayout với ViewPager2
        new TabLayoutMediator(tablayout, viewPager2, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Video");
//                    searchBar.setVisibility(View.VISIBLE); // Hiển thị SearchView khi ở tab Video
                    break;
                case 1:
                    tab.setText("Tìm Kiếm");
//                    searchBar.setVisibility(View.GONE); // Ẩn SearchView khi ở tab Lịch sử
                    break;
                case 2:
                    tab.setText("Lịch sử");
//                    searchBar.setVisibility(View.GONE); // Ẩn SearchView khi ở tab Cá nhân
                    break;
                case 3:
                    tab.setText("Cá nhân");
                    break;
            }
        }).attach();


        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 0 || position == 1) {
                    // Hiển thị SearchView ở tab Video
                    searchBar.setVisibility(View.VISIBLE);
                } else {
                    // Ẩn SearchView ở các tab khác
                    searchBar.setVisibility(View.GONE);
                }
                if (position == 2) { // Tab Lịch sử
                    Fragment currentFragment = getSupportFragmentManager()
                            .findFragmentByTag("f" + position);
                    if (currentFragment instanceof hisFragment) {
                        ((hisFragment) currentFragment).refreshData(); // Làm mới dữ liệu lịch sử
                    }
                }
            }
        });
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Khi người dùng nhấn tìm kiếm, chuyển đến tab "Tìm kiếm"
                viewPager2.setCurrentItem(1);  // Tab Tìm kiếm
                // Gọi phương thức tìm kiếm trong SearchFragment với từ khóa
                SearchFragment fragment = (SearchFragment) getSupportFragmentManager().findFragmentByTag("f" + viewPager2.getCurrentItem());
                if (fragment != null) {
                    fragment.searchVideos(query);  // Gửi từ khóa tìm kiếm vào SearchFragment
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Tìm kiếm khi văn bản thay đổi
                if (viewPager2.getCurrentItem() == 1) {  // Chỉ tìm kiếm khi đang ở tab Tìm kiếm
                    SearchFragment fragment = (SearchFragment) getSupportFragmentManager().findFragmentByTag("f" + viewPager2.getCurrentItem());
                    if (fragment != null) {
                        fragment.searchVideos(newText);  // Gửi từ khóa tìm kiếm vào SearchFragment
                    }
                }
                return false;
            }
        });
    }

    void initTablayout(){
        tablayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.viewPager);
        searchBar = findViewById(R.id.search_bar);
    }

}


