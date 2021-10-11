package com.br.systock.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.br.systock.R;
import com.br.systock.view.tutorialfragments.TutorialFragment1;
import com.br.systock.view.tutorialfragments.TutorialFragment2;
import com.br.systock.view.tutorialfragments.TutorialFragment3;
import com.br.systock.view.tutorialfragments.TutorialFragment4;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.util.ArrayList;
import java.util.List;

public class TutorialActivity extends AppCompatActivity {

    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private WormDotsIndicator dotsIndicator;
    private Button skip;
    private Button next;
    private boolean isLastPageSwiped;
    private int counterPageScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);



        List<Fragment> list = new ArrayList<>();
        list.add(new TutorialFragment1());
        list.add(new TutorialFragment2());
        list.add(new TutorialFragment3());
        list.add(new TutorialFragment4());

        skip = findViewById(R.id.button8);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbrirMainActivity();

            }
        });
        Button next = (Button)findViewById(R.id.button9);
        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                pager.setCurrentItem(getItem(+1), true); //getItem(-1) for previous
                if(pager.getCurrentItem() == 3)
                {
                    next.setVisibility(View.GONE);
                }


                

            }
        });







        pager = findViewById(R.id.view_pager);
        pagerAdapter = new SliderPagerAdapter(getSupportFragmentManager(),list);
        pager.setAdapter(pagerAdapter);

        dotsIndicator = (WormDotsIndicator) findViewById(R.id.dots_indicator);
        pager = (ViewPager) findViewById(R.id.view_pager);
        pagerAdapter = new SliderPagerAdapter(getSupportFragmentManager(),list);
        pager.setAdapter(pagerAdapter);
        dotsIndicator.setViewPager(pager);


    }
    public void AbrirMainActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    private int getItem(int i) {
        return pager.getCurrentItem() + i;

    }

        }





