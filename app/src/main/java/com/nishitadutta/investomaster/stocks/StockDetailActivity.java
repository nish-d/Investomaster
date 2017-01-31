package com.nishitadutta.investomaster.stocks;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.nishitadutta.investomaster.utils.AppConstants;
import com.nishitadutta.investomaster.R;
import com.nishitadutta.investomaster.utils.SharedPreferenceUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

public class StockDetailActivity extends AppCompatActivity {
    private static final String TAG = "StockDetailActivity";
    String symbol;
    StockObject stockObject;
    Stock stock;
    List<HistoricalQuote> history;
    Interval interval=Interval.MONTHLY;
    @BindView(R.id.stock_detail_toolbar)
    Toolbar toolbar;
@BindView(R.id.detail_progress)
    ProgressBar progressBar;
    @BindView(R.id.chart)
    LineChartView lineChartView;

    @BindView(R.id.et_bid)
    TextInputEditText etBid;

    @BindView(R.id.et_close)
    TextInputEditText etClose;

    @BindView(R.id.et_open)
    TextInputEditText etOpen;

    @BindView(R.id.et_company)
    TextInputEditText etCompany;

    @BindView(R.id.et_high)
    TextInputEditText etHigh;

    @BindView(R.id.et_low)
    TextInputEditText etLow;

    @BindView(R.id.et_volume)
    TextInputEditText etVolume;

    @BindView(R.id.btn_set)
    Button btnSet;

    @BindView(R.id.spinner_interval)
    Spinner spinnerInterval;

    @BindView(R.id.date_picker)
    DatePicker datePicker;
    ArrayAdapter<CharSequence> arrayAdapter;
    private LineChartData data;
    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasLines = true;
    private boolean hasPoints = false;
    private ValueShape shape = ValueShape.CIRCLE;
    private boolean isFilled = false;
    private boolean hasLabels = false;
    private boolean isCubic = false;
    private boolean hasLabelForSelected = false;
    private boolean pointsHaveDifferentColor;

    final Calendar c = Calendar.getInstance();
Calendar from=Calendar.getInstance();
    int maxYear = c.get(Calendar.YEAR) ; // this year ( 2016 )
    int maxMonth = c.get(Calendar.MONTH);
    int maxDay = c.get(Calendar.DAY_OF_MONTH);

    int minYear = 2005;
    int minMonth = 0; // january
    int minDay = 25;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_detail);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        symbol = bundle.getString(AppConstants.STOCK_OBJECT_KEY);
        history = new ArrayList<>();
        from.add(Calendar.YEAR,-1);
        new StockHistoryJob().execute();
        toolbar.setTitle(symbol);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        setUneditable(etHigh);
        setUneditable(etLow);
        setUneditable(etClose);
        setUneditable(etOpen);
        setUneditable(etVolume);
        setUneditable(etBid);
        setUneditable(etCompany);

        arrayAdapter = ArrayAdapter.createFromResource(this,
                R.array.interval_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the arrayAdapter to the spinner
        spinnerInterval.setAdapter(arrayAdapter);

        spinnerInterval.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        interval=Interval.DAILY;
                        break;
                    case 1:
                        interval=Interval.WEEKLY;
                        break;
                    case 2:
                        interval=Interval.MONTHLY;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                    interval=Interval.MONTHLY;
            }
        });

        datePicker.init(maxYear, maxMonth, maxDay, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                from=Calendar.getInstance();
                int y=year-maxYear;
                int m=monthOfYear-maxMonth;
                int d=dayOfMonth-maxDay;

                from.add(Calendar.YEAR,y);
                from.add(Calendar.MONTH,m);
                from.add(Calendar.DAY_OF_MONTH,d);
            }
        });
    }
    @OnClick(R.id.btn_set)
    public void resetGraph(){
        new StockHistoryJob().execute();
    }


    private void setChartData() {
        int numberOfLines = 1;
        int numberOfPoints = history.size();
        List<Line> lines = new ArrayList<Line>();
        Collections.reverse(history);
        List<PointValue> values = new ArrayList<PointValue>();
        List<AxisValue> axisValues = new ArrayList<>();
        SimpleDateFormat ft = new SimpleDateFormat("dd MMM yy");
        for (int i = 0; i < numberOfLines; ++i) {


            for (int j = 0; j < numberOfPoints; ++j) {
                values.add(new PointValue(j, history.get(j).getClose().floatValue()));
                axisValues.add(new AxisValue(j).setLabel(ft.format(history.get(j).getDate().getTime())));
            }

            Line line = new Line(values);
            line.setColor(ChartUtils.COLORS[i]);
            line.setShape(shape);
            line.setCubic(isCubic);
            line.setFilled(isFilled);
            line.setHasLabels(hasLabels);
            line.setHasLabelsOnlyForSelected(hasLabelForSelected);
            line.setHasLines(hasLines);
            line.setHasPoints(hasPoints);
            line.setStrokeWidth(1);
            if (pointsHaveDifferentColor) {
                line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
            }
            lines.add(line);
        }

        data = new LineChartData(lines);

        if (hasAxes) {
            Axis axisX = new Axis(axisValues).setHasTiltedLabels(true);
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                axisX.setName("");
                axisY.setName("");
            }

            data.setAxisXBottom(axisX);
            data.setAxisYRight(axisY);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }

        data.setBaseValue(Float.NEGATIVE_INFINITY);
        lineChartView.setLineChartData(data);

    }

    public void setUneditable(TextInputEditText uneditable) {
        uneditable.setEnabled(false);
        uneditable.setFocusable(false);
    }

    private void setInfoData() {
        progressBar.setVisibility(View.GONE);
        etCompany.setText(stock.getName());
        etOpen.setText(stock.getQuote().getOpen().toString());
        etClose.setText(stock.getQuote().getPreviousClose().toString());
        etBid.setText(stock.getQuote().getBid().toString());
        etHigh.setText(stock.getQuote().getDayHigh().toString());
        etLow.setText(stock.getQuote().getDayLow().toString());
        etVolume.setText(stock.getQuote().getVolume().toString());
    }

    private class StockHistoryJob extends AsyncTask<String, Void, Void> {


        @Override
        protected Void doInBackground(String... params) {
            try {
                Calendar to = Calendar.getInstance();
                stock = YahooFinance.get(symbol, true);
                history = stock.getHistory(from, to,interval);

            } catch (IOException e) {
                System.out.println(e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (history != null) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setChartData();
                    }
                });

            } else {
                Toast.makeText(StockDetailActivity.this, "Check your internet connection and try again.", Toast.LENGTH_SHORT).show();
            }
            if (stock != null) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setInfoData();
                    }
                });

            } else {
                Toast.makeText(StockDetailActivity.this, "Check your internet connection and try again.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
