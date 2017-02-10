package com.behanga;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.behanga.view.PageView;


public class MainActivity extends AppCompatActivity {

	private PageView mPageView;
	private Button mButton;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mPageView = (PageView) findViewById(R.id.pageView);
		mButton = (Button) findViewById(R.id.add_text);

		mButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				mPageView.mPage.paragraphs.get(0).lines.get(0).addSpan(0,);
			}
		});
	}
}
