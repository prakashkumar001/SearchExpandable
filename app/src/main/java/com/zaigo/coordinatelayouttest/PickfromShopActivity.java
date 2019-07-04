package com.zaigo.coordinatelayouttest;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.zaigo.coordinatelayouttest.model.DataPassModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PickfromShopActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener {




    List<String> storeList;
    Spinner spin_store;
    String str_store="";
    private TimePickerDialog tpd;

   TextView txt_time,txt_date;


    Button btn_submit,btn_cancel;
    String str_states="",str_subtotal="",str_date="",str_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#000000"));
        }

        setContentView(R.layout.activity_pickfrom_shop);


        Intent i = getIntent();

        str_subtotal=i.getStringExtra("subtotal");
        str_states= i.getStringExtra("state");

        spin_store = (Spinner)findViewById(R.id.spin_store);

        txt_date =(TextView)findViewById(R.id.txt_date);
        txt_time =(TextView)findViewById(R.id.txt_time);

        btn_submit = (Button)findViewById(R.id.btn_submit);

        btn_cancel = (Button)findViewById(R.id.btn_cancel);


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!str_date.isEmpty() && !str_time.isEmpty()){
                    Intent i = new Intent(PickfromShopActivity.this,MainDeliveryActivity.class);


                    DataPassModel dpm = new DataPassModel();

                    dpm.setShip_opt("1");
                    dpm.setShip_opt_date(str_date);
                    dpm.setShip_opt_time(str_time);
                    dpm.setShip_opt_shop(str_store);

//Create the bundle
                    Bundle bundle = new Bundle();

//Add your data to bundle
                    bundle.putString("stuff", "pickfromshop");

//Add the bundle to the intent
                    i.putExtras(bundle);

                    i.putExtra("pickfromshop","pickfromshop");
                    i.putExtra("subtotal",str_subtotal);
                    i.putExtra("state",str_states);
                    startActivity(i);
                    finish();
                }else{

                    Toast.makeText(PickfromShopActivity.this, "Please select date and time", Toast.LENGTH_SHORT).show();
                }


            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PickfromShopActivity.this,MainDeliveryActivity.class);


                DataPassModel dpm = new DataPassModel();

                dpm.setShip_opt("");
                dpm.setShip_opt_date("");
                dpm.setShip_opt_time("");
                dpm.setShip_opt_shop("");

                i.putExtra("pickfromshop","nopickfromshop");
                i.putExtra("subtotal",str_subtotal);
                i.putExtra("state",str_states);
                startActivity(i);
                finish();
            }
        });
        showDatePicker();
       /* final Handler someHandler = new Handler(getMainLooper());
        someHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tvClock.setText(new SimpleDateFormat("HH:mm", Locale.US).format(new Date()));
                someHandler.postDelayed(this, 1000);
            }
        }, 10);
*/
        txt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });


        txt_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });

        dateToCalendar();
       /* SHEIKH ZAYED ROAD
        THE GREENS
        GOLDEN MILE
        MIRDIF*/
        storeList = new ArrayList<String>();
        storeList.add("SHEIKH ZAYED ROAD");
        storeList.add("THE GREENS");
        storeList.add("GOLDEN MILE");
        storeList.add("MIRDIF");
        ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, storeList);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_store.setAdapter(stateAdapter);

        spin_store.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                //Change the selected item's text color

                str_store = storeList.get(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

        /*
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                PickfromShopActivity.this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
        );*/
// If you're calling this from a support Fragment
       // dpd.show(getSupportFragmentManager(), "Datepickerdialog");
       // showDatePicker();

    }

    public void showTimePicker(){

        Calendar calendar = Calendar.getInstance();

        tpd = TimePickerDialog.newInstance(
                this,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
               false
        );
        tpd.show(PickfromShopActivity.this.getFragmentManager(), "Timepickerdialog");
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH");

        SimpleDateFormat currentdatesdf = new SimpleDateFormat("EEEE");

        String currentDateandTime = sdf.format(new Date());

        String str_currentdate = currentdatesdf.format(new Date());




        int currtime =Integer.parseInt(currentDateandTime);
        System.out.println("timeeeeeeeeeeeeeeeee"+currentDateandTime+","+currtime+","+str_currentdate);


        if(str_currentdate.trim().equalsIgnoreCase("Thursday")){
            int year = calendar.get(Calendar.YEAR);

            int month = calendar.get(Calendar.MONTH)-1;

            int day = calendar.get(Calendar.DATE)+2;

            System.out.println("vvvvvvvvvvvvvvvvvvv111"+day);

            calendar.set(year, month, day);
            calendar.add(Calendar.MONTH, 1);

        }else{


            if(currtime>16){

                System.out.println("trueeeeeeeeeeee");

                int year = calendar.get(Calendar.YEAR);

                int month = calendar.get(Calendar.MONTH)-1;

                int day = calendar.get(Calendar.DATE)+2;

                System.out.println("vvvvvvvvvvvvvvvvvvv222"+day);

                calendar.set(year, month, day);
                calendar.add(Calendar.MONTH, 1);


            }else{
                int year = calendar.get(Calendar.YEAR);

                int month = calendar.get(Calendar.MONTH)-1;

                int day = calendar.get(Calendar.DATE)+1;

                System.out.println("vvvvvvvvvvvvvvvvvvv333"+day);

                calendar.set(year, month, day);
                calendar.add(Calendar.MONTH, 1);
                System.out.println("falseeeeeeeeeeeeeee");
            }


        }






        //convert them to int




        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                calendar.get(calendar.YEAR),
                calendar.get(calendar.MONTH),
                calendar.get(calendar.DAY_OF_MONTH)
        );
        dpd.show(PickfromShopActivity.this.getFragmentManager(), "DatePickerDialog");

        dpd.setMinDate(calendar);


        Calendar sunday,saturday;
        List<Calendar> weekends = new ArrayList<>();
        int weeks = 115;

        for (int i = 0; i < (weeks * 7); i = i + 7) {
            for(int j =0; j > (weeks*7) ; j = j - 7);
          //  sunday = Calendar.getInstance();
         //   sunday.add(Calendar.DAY_OF_YEAR, (Calendar.SUNDAY - sunday.get(Calendar.DAY_OF_WEEK) + 7 + i));
             saturday = Calendar.getInstance();
             saturday.add(Calendar.DAY_OF_YEAR, (Calendar.FRIDAY - saturday.get(Calendar.DAY_OF_WEEK) + i));
             weekends.add(saturday);
          //  weekends.add(sunday);
        }
        Calendar[] disabledDays = weekends.toArray(new Calendar[weekends.size()]);
        dpd.setDisabledDays(disabledDays);

    }

    private void dateToCalendar() {

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
        String formattedDate = df.format(c);
        txt_date.setText("Select Date");
      //  txt_time.setText(""+date.getTime());
        Calendar cal = Calendar.getInstance();
       str_time = ""+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE);


        DateFormat dfff = new SimpleDateFormat("HH:mm");

        DateFormat outputformat = new SimpleDateFormat( "hh:mm aa");

        try {
            Date date= dfff.parse(str_time);
            //old date format to new date format
            str_time = outputformat.format(date);
            txt_time.setText(str_time);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
     //   txt_time.setText(str_time);


    }


    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
         str_time = ""+hourOfDay+":"+minute;



        DateFormat df = new SimpleDateFormat("HH:mm");

        DateFormat outputformat = new SimpleDateFormat( "hh:mm aa");

        try {
            Date date= df.parse(str_time);
            //old date format to new date format
            str_time = outputformat.format(date);
            txt_time.setText(str_time);
        } catch (final ParseException e) {
            e.printStackTrace();
        }







    }
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = ""+dayOfMonth+"-"+(++monthOfYear)+"-"+year;
        try{
            SimpleDateFormat your_format = new SimpleDateFormat("dd-MMM-yyyy");

            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

            Date dt = format.parse(date);
            str_date = your_format.format(dt);

            txt_date.setText(str_date);

    }catch (ParseException e1) {
// TODO Auto-generated catch block
        e1.printStackTrace();
    }


       // dpd = null;
    }

    @Override
    public void onBackPressed() {

    }
}
