/*
Built by: Peter Lewis
Date: March 11, 2016
Description: This app displays the date which you tap on as a string.
 */

package com.example.lynnjames.birthdaycalendar;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private int year;
    private int month;
    private int day;
    //cannot find components yet because the activity_main.xml file has yet to have been read. Therefore the components must not be initialized yet.
    private TextView headlineTextView;
    private TextView quoteTextView;
    private CalendarView calendarView;
    private EditText yearInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//activity_main.xml file being read

        //get components (all components can now be found.)
        headlineTextView = (TextView) findViewById(R.id.headlineTextView);//
        quoteTextView = (TextView) findViewById(R.id.quoteTextView);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        yearInput = (EditText)findViewById(R.id.yearInput);

        //add "Done" button listener for keyboard
        yearInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    //clear the input focus
                    yearInput.clearFocus();
                    //force keyboard down
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(calendarView.getWindowToken(), 0);
                    String yearSt = yearInput.getText().toString();
                    //since the input is for integers only, the if statement is probably not needed but someone may have a physical keyboard
                    if(yearSt.matches("[0-9]+")&&yearSt.length()<=4){
                        goToYear(Integer.parseInt(yearSt));
                    }else if(yearSt.isEmpty()){

                        headlineTextView.setText("");
                        quoteTextView.setText("");
                    }else{
                        headlineTextView.setText("That's not a date!");
                        quoteTextView.setText("");

                    }
                }
                return false;
            }
        });

        //add calendar listener for the date change
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int Year, int Month, int dayOfMonth) {
                year = Year;
                month = Month;
                day = dayOfMonth;
                dateChanged();
            }
        });
    }

    private void dateChanged() {
        //get the current date
        Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DAY = calendar.get(Calendar.DAY_OF_MONTH);
        //make the month a string and set the quote
        String monthString;
        String quote;
        switch (month) {
            case 0:
                monthString = "January";
                quote = "\"When one reads a poet in January, it is as lovely as when one goes to walk in June.\" - Jean Paul Friedrich Richter"; // from http://www.quotegarden.com
                break;
            case 1:
                monthString = "February";
                quote = "\"February days are a marketing gimmick; love happens every day.\" - Randeep Hooda"; // from https://www.brainyquote.com/
                break;
            case 2:
                monthString = "March";
                quote = "\"March on. Don't look in the rearview, just the windshield.\" - Josh Bowman"; // from https://www.brainyquote.com/
                break;
            case 3:
                monthString = "April";
                quote = "\"April is a promise that May is bound to keep.\" - Hal Borland"; // from https://www.brainyquote.com/
                break;
            case 4:
                monthString = "May";
                quote = "\"The month of May was come, when every lusty heart beginneth to blossom, and to bring forth fruit\" - Thomas Malory"; // from https://www.brainyquote.com/
                break;
            case 5:
                monthString = "June";
                quote = "\"There are two seasons in Scotland: June and Winter.\" - Billy Connolly"; // from https://www.brainyquote.com/
                break;
            case 6:
                monthString = "July";
                quote = "\"A perfect summer day is when the sun is shining, the breeze is blowing, the birds are singing, and the lawn mower is broken.\" - James Dent"; // http://www.quotegarden.com
                break;
            case 7:
                monthString = "August";
                quote = "\"August was nearly over - the month of apples and falling stars, the last care-free month for the school children.\" - Victor Nekrasov"; // from https://www.goodreads.com
                break;
            case 8:
                monthString = "September";
                quote = "\"I love September, especially when we're in it.\" - Willie Stargell"; // from https://www.brainyquote.com/
                break;
            case 9:
                monthString = "October";
                quote = "\"Upstate New York in the middle of October. You can't get more beautiful than that.\" - Paul Reiser"; // from https://www.brainyquote.com/
                break;
            case 10:
                monthString = "November";
                quote = "\"November comes And November goes, With the last red berries And the first white snows.\" - Clyde Watson"; // from https://www.goodreads.com
                break;
            case 11:
                monthString = "December";
                quote = "\"God gave us memory so that we might have roses in December.\" - James M. Barrie"; // from https://www.brainyquote.com/
                break;
            default:
                monthString = "";
                quote = "";
                break;
        }
        if(YEAR<year||(YEAR==year&&MONTH<month)||(YEAR==year&&MONTH==month&&DAY<day)){//not born yet
            headlineTextView.setText("You will be born on "+monthString+" "+day+", "+year);
        }else if(YEAR-70>year){//approximately older than 70 years
            headlineTextView.setText("You were born on "+monthString+" "+day+", "+year);
            quote = "\"Let us never know what old age is. Let us know the happiness time brings, not count the years.\" - Ausonius";
        }else{
            headlineTextView.setText("You were born on "+monthString+" "+day+", "+year);
        }
        quoteTextView.setText(quote);
    }

    private void goToYear(int year){
        headlineTextView.setText("");
        quoteTextView.setText("");
        long millSecYear = Math.round((year-1970)*(3.1558 * (Math.pow(10, 10)))); // 31536000000 is one year in milliseconds
        calendarView.setDate(millSecYear, false, false);
    }

}