package com.art.summary;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	
	private static final int ANSWER_REQUEST_CODE = 100;
	private int DIALOG_DATE = 1;
	private int bdYear = 1990;
	private int bdMonth = 0;
	private int bdDay = 1;
	private boolean birthDaydIsChange = false;
	
	private TextView tvBirthday;
	private Button btnSend;
	
	private EditText edtName;
	private Spinner spSex;
	private EditText edtJob;
	private EditText edtSalary;
	private EditText edtPhone;
	private EditText edtEmail;
	
	private static final String EDT_NAME_STATE_KEY = "EDT_NAME_STATE_KEY";
	private static final String SP_SEX_STATE_KEY = "SP_SEX_STATE_KEY";
	private static final String EDT_JOB_STATE_KEY = "EDT_JOB_STATE_KEY";
	private static final String EDT_SALARY_STATE_KEY = "EDT_SALARY_STATE_KEY";
	private static final String EDT_PHONE_STATE_KEY = "EDT_PHONE_STATE_KEY";
	private static final String EDT_EMAIL_STATE_KEY = "EDT_EMAIL_STATE_KEY";
	private static final String BD_YEAR_STATE_KEY = "BD_YEAR_STATE_KEY";
	private static final String BD_MONTH_STATE_KEY = "BD_MONTH_STATE_KEY";
	private static final String BD_DAY_STATE_KEY = "BD_DAY_STATE_KEY";
	private static final String BD_IS_CHANGE_STATE_KEY = "BD_IS_CHANGE_STATE_KEY";
	
	private AlertDialog.Builder ad;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		edtName = (EditText)findViewById(R.id.edtName);
		spSex = (Spinner)findViewById(R.id.spSex);
		edtJob = (EditText)findViewById(R.id.edtJob);
		edtSalary = (EditText)findViewById(R.id.edtSalary);
		edtPhone = (EditText)findViewById(R.id.edtPhone);
		edtEmail = (EditText)findViewById(R.id.edtEmail);
		
		tvBirthday = (TextView)findViewById(R.id.tvBirthday);
		tvBirthday.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DIALOG_DATE);
			}
		});
		
		btnSend = (Button)findViewById(R.id.btnSend);
		btnSend.setOnClickListener(this);
		
		ad = new AlertDialog.Builder(this);
	    ad.setTitle(R.string.answer_hint);
	    ad.setCancelable(false);
        ad.setPositiveButton("Повторить", new android.content.DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
            }
        });
        ad.setNegativeButton("Закрыть", new android.content.DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
            	finish();
            }
        });
        
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		
		super.onSaveInstanceState(outState);
		
		outState.putString(EDT_NAME_STATE_KEY,edtName.getText().toString());
		outState.putString(EDT_JOB_STATE_KEY,edtJob.getText().toString());
		outState.putString(EDT_SALARY_STATE_KEY,edtSalary.getText().toString());
		outState.putString(EDT_PHONE_STATE_KEY,edtPhone.getText().toString());
		outState.putString(EDT_EMAIL_STATE_KEY,edtEmail.getText().toString());
		outState.putInt(SP_SEX_STATE_KEY,spSex.getSelectedItemPosition());
		outState.putInt(BD_YEAR_STATE_KEY,bdYear);
		outState.putInt(BD_MONTH_STATE_KEY,bdMonth);
		outState.putInt(BD_DAY_STATE_KEY,bdDay);
		outState.putBoolean(BD_IS_CHANGE_STATE_KEY,birthDaydIsChange);
		
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		
		super.onRestoreInstanceState(savedInstanceState);
		
		edtName.setText(savedInstanceState.getString(EDT_NAME_STATE_KEY));
		edtJob.setText(savedInstanceState.getString(EDT_JOB_STATE_KEY));
		edtSalary.setText(savedInstanceState.getString(EDT_SALARY_STATE_KEY));
		edtPhone.setText(savedInstanceState.getString(EDT_PHONE_STATE_KEY));
		edtEmail.setText(savedInstanceState.getString(EDT_EMAIL_STATE_KEY));
		
		spSex.setSelection(savedInstanceState.getInt(SP_SEX_STATE_KEY));
		
		bdYear  = savedInstanceState.getInt(BD_YEAR_STATE_KEY);
		bdMonth = savedInstanceState.getInt(BD_MONTH_STATE_KEY);
		bdDay   = savedInstanceState.getInt(BD_DAY_STATE_KEY);
		
		birthDaydIsChange = savedInstanceState.getBoolean(BD_IS_CHANGE_STATE_KEY);
		
		refreshUI();
		
	}
	
	private void refreshUI() {
		refreshBirthDayText();
	}

	private void refreshBirthDayText() {
		if(birthDaydIsChange){
			tvBirthday.setText(getResources().getString(R.string.birthday_hint)+" "+getDataText());
		}
		
	}

	private String getDataText() {
		return birthDaydIsChange ? ("" + bdDay + "/" + (bdMonth+1) + "/" + bdYear):"";
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btnSend:
			String summary_data =  "" +
			        "ФИО: " + edtName.getText().toString() + "\n" +
			        "Дата рождения: " + getDataText() + "\n" +
			        "Пол: " + spSex.getSelectedItem().toString() + "\n" +
			        "Должность: " + edtJob.getText().toString() + "\n" +
			        "Зарплата: " + edtSalary.getText().toString() + "\n" +
			        "Телефон: " + edtPhone.getText().toString() + "\n" +
			        "E-mail: " + edtEmail.getText().toString() + "\n"; 
			
			Intent intent = new Intent();
			intent.setClass(this, AnswerActivity.class);
			intent.putExtra("summary_data", summary_data);
			
			startActivityForResult(intent, ANSWER_REQUEST_CODE);
			break;
		}
		
	}
	
	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		if (id == DIALOG_DATE) {
	    	DatePickerDialog tpd = new DatePickerDialog(this, birthDayCallBack, bdYear, bdMonth, bdDay);
	    	return tpd;
		}
		
		return super.onCreateDialog(id);
	}	

    OnDateSetListener birthDayCallBack = new OnDateSetListener() {

	    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
	    	bdYear = year;
	    	bdMonth = monthOfYear;
	    	bdDay = dayOfMonth;
	    	birthDaydIsChange = true;
	    	refreshBirthDayText();
	    }
    
    };
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(requestCode == ANSWER_REQUEST_CODE){
        	if(resultCode == RESULT_OK && data != null){
        		String answer = data.getStringExtra("answer");
        		ad.setMessage(answer);
        		ad.show();
        	}else{
        		Toast.makeText(this, "Ответ не получен!", Toast.LENGTH_LONG).show();
        	}
    	}
    };
	
}
