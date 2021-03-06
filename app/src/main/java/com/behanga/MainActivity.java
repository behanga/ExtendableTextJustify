package com.behanga;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.behanga.core.State;
import com.behanga.element.ImageBlock;
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
				ImageBlock block = (ImageBlock) mPageView.mPage.paragraphs.get(0).blocks.get(0);
				block.width += 100;
				block.height += 100;
				State state = new State();
				state.widthChangeType = State.INCREASE;
				state.heightChangeType = State.INCREASE;
				state.beginer = block;
				block.notifyChange(state);
			}
		});


	}
}
