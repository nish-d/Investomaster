package com.nishitadutta.investomaster.compare;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nishitadutta.investomaster.R;
import com.nishitadutta.investomaster.stocks.StockDetailActivity;
import com.nishitadutta.investomaster.utils.AppConstants;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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

public class CompareActivity extends AppCompatActivity {
    @BindView(R.id.c_progress)
    ProgressBar progressBar;

    @BindView(R.id.c_line_chart)
    LineChartView lineChartView;
    Stock stock,stock2;
    List<HistoricalQuote> history,history2;
    @BindView(R.id.stock_toolbar)
    Toolbar toolbar;
String symbol, symbol2;
    private LineChartData data;
    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasLines = true;
    private boolean hasPoints = true;
    private ValueShape shape = ValueShape.CIRCLE;
    private boolean isFilled = false;
    private boolean hasLabels = false;
    private boolean isCubic = false;
    private boolean hasLabelForSelected = false;
    private boolean pointsHaveDifferentColor=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);
        ButterKnife.bind(this);
        toolbar.setTitle("Compare");
        toolbar.setTitleTextColor(Color.WHITE);
        symbol = getIntent().getStringExtra(AppConstants.RED_SYM);
        symbol2=getIntent().getStringExtra(AppConstants.BLUE_SYM);

        history = new ArrayList<>();
        history2=new ArrayList<>();
new StockHistoryJob().execute();


    }

    private void setChartData() {
        progressBar.setVisibility(View.GONE);
        int numberOfLines = 2;
        int numberOfPoints = history.size();
        List<Line> lines = new ArrayList<Line>();
        Collections.reverse(history);
        List<PointValue> values = new ArrayList<PointValue>();
        List<PointValue> values2 = new ArrayList<PointValue>();
        List<AxisValue> axisValues = new ArrayList<>();
        List<AxisValue> axisValues2 = new ArrayList<>();
        SimpleDateFormat ft = new SimpleDateFormat("dd MMM yy");
        for (int i = 1; i < numberOfLines; ++i) {


            for (int j = 0; j < numberOfPoints; ++j) {
                values.add(new PointValue(j, history.get(j).getClose().floatValue()));
                axisValues.add(new AxisValue(j).setLabel(ft.format(history.get(j).getDate().getTime())));
            }

            Line line = new Line(values);
            line.setColor(ChartUtils.COLORS[0]);
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

        for (int i = 0; i < numberOfLines-1; ++i) {


            for (int j = 0; j < numberOfPoints; ++j) {
                values2.add(new PointValue(j, history2.get(j).getClose().floatValue()));
                axisValues2.add(new AxisValue(j).setLabel(ft.format(history2.get(j).getDate().getTime())));
            }

            Line line = new Line(values2);
            line.setColor(Color.RED);
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
    private class StockHistoryJob extends AsyncTask<String, Void, Void> {


        @Override
        protected Void doInBackground(String... params) {
            try {
                Calendar to = Calendar.getInstance();
                stock = YahooFinance.get(symbol, true);
                stock2=YahooFinance.get(symbol2,true);
                history = stock.getHistory();
                history2=stock2.getHistory();

            } catch (IOException e) {
                Toast.makeText(CompareActivity.this, "Some data could not retrieved. Have you entered correct symbols?", Toast.LENGTH_LONG).show();
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

                Toast.makeText(CompareActivity.this, "Check your internet connection and try again.", Toast.LENGTH_LONG).show();
            }

        }
    }
}
