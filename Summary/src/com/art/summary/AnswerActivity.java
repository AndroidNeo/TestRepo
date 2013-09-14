package com.art.summary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AnswerActivity extends Activity {
	
	private Button btnAnswer;
	private EditText edtAnswer;
	private TextView tvSummaryData;
	
	private static final String EDT_ANSWER_STATE_KEY = "EDT_ANSWER_STATE_KEY";
	private static final String TV_SUMMARY_DATA_STATE_KEY = "TV_SUMMARY_DATA_STATE_KEY";
	
	private String summaryData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_answer);
		
		tvSummaryData = (TextView)findViewById(R.id.tvSummaryData);		
		edtAnswer = (EditText)findViewById(R.id.edtName);
		
		btnAnswer = (Button)findViewById(R.id.btnAnswer);
		btnAnswer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("answer", edtAnswer.getText().toString());
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		
		summaryData = this.getIntent().getExtras().getString("summary_data");
		refreshUI();
		
	}

	private void refreshUI() {
		refreshSummaryData();
	}

	private void refreshSummaryData() {
		tvSummaryData.setText(summaryData);
		Linkify.addLinks(tvSummaryData, Linkify.PHONE_NUMBERS|Linkify.EMAIL_ADDRESSES);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		
		super.onSaveInstanceState(outState);
		
		outState.putString(TV_SUMMARY_DATA_STATE_KEY,summaryData);
		outState.putString(EDT_ANSWER_STATE_KEY,edtAnswer.getText().toString());
		
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		
		super.onRestoreInstanceState(savedInstanceState);
		
		summaryData = savedInstanceState.getString(TV_SUMMARY_DATA_STATE_KEY);
		edtAnswer.setText(savedInstanceState.getString(EDT_ANSWER_STATE_KEY));
		refreshUI();
	}

}
