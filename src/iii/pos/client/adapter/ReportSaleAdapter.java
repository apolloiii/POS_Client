/*package iii.pos.client.adapter;

import iii.pos.client.R;
import iii.pos.client.activity.ReportSale_PaymentActivity;
import iii.pos.client.model.Report;
import iii.pos.client.model.ReportItem;
import iii.pos.client.model.Report_SPay;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ReportSaleAdapter extends ArrayAdapter<Report> {
	private Context context;
	private ArrayList<Report> lstReventue;
	private ArrayList<Report_SPay> lstRevenueSpending;
	private ArrayList<ReportItem> lstReportItem;
	private int KEY = 1;

	public ReportSaleAdapter(Context context, int resource,
			ArrayList arrayList, int KEY) {
		super(context, resource, arrayList);
		this.context = context;
		this.KEY = KEY;
		switch (KEY) {
		case ReportSale_PaymentActivity.REVENUE:
			this.lstReventue = arrayList;
			break;
		case ReportSale_PaymentActivity.REVENUE_SPENDING:
			this.lstRevenueSpending = arrayList;
			break;
		case ReportSale_PaymentActivity.REPORT_ITEM:
			this.lstReportItem = arrayList;

			break;
		case ReportSale_PaymentActivity.REPORT_INVOICE:

			break;
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		try {
			if (convertView == null) {
				holder = new ViewHolder();
				LayoutInflater inflate = (LayoutInflater) this.context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflate.inflate(R.layout.item_reportsale_layout,
						null);
				-----------------------------------------------------------
				holder.reportitem = (LinearLayout) convertView
						.findViewById(R.id.reportitem);
				holder.txtstt = (TextView) convertView
						.findViewById(R.id.txtStt);
				holder.txtDate = (TextView) convertView
						.findViewById(R.id.txtDate);
				holder.txtTotalreport = (TextView) convertView
						.findViewById(R.id.txtTotalreport);
				holder.txtTotalValue = (TextView) convertView
						.findViewById(R.id.txtTotalValue);
				holder.txtAverage = (TextView) convertView
						.findViewById(R.id.txtAverage);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.txtstt.setText(String.valueOf(position + 1));
			if (position % 2 == 0) {
				holder.reportitem.setBackgroundColor(context.getResources()
						.getColor(R.color.report_item_color1));
			} else {
				holder.reportitem.setBackgroundColor(context.getResources()
						.getColor(R.color.report_item_color2));
			}
			switch (KEY) {
			case ReportSale_PaymentActivity.REVENUE:
				final Report report = lstReventue.get(position);
				
				holder.txtDate.setText(returnDate(report.getDatetime()));
				holder.txtTotalreport.setText(String.valueOf(report
						.getTotalReport()));
				holder.txtTotalValue.setText(formatDecimal(report
						.getTotalValue()));
				holder.txtAverage.setText(formatDecimal(report.getTotalValue()
						/ report.getTotalReport()));
				break;
			case ReportSale_PaymentActivity.REVENUE_SPENDING:
				final Report_SPay report1 = lstRevenueSpending.get(position);
				
				holder.txtDate.setText(returnDate(report1.getDate()));
				// holder.txtDate.setText(report1.getDate());
				holder.txtTotalreport.setText(String.valueOf(report1
						.getTotalRevenue()));
				holder.txtTotalValue.setText(String.valueOf(report1
						.getTotalSpending()));
				holder.txtAverage.setText(String.valueOf(0));
				break;
			case ReportSale_PaymentActivity.REPORT_ITEM:
				final ReportItem report11 = lstReportItem.get(position);
				holder.txtDate.setText(report11.getName());
				holder.txtTotalreport.setText(String.valueOf(report11
						.getAmount()));
				holder.txtTotalValue.setText(context.getResources().getString(
						R.string.report_unit));
				holder.txtAverage.setText(formatDecimal(report11.getPrice())
						+ context.getResources().getString(R.string.curr));
				break;
			case ReportSale_PaymentActivity.REPORT_INVOICE:

				break;

			}

		} catch (Exception e) {
		}
		return convertView;
	}

	// --------return date--------------------//
	private String returnDate(String str) {
		String year = str.substring(0, 4);
		String month = str.substring(4, 6);
		String day = str.substring(6, 8);
		String mydate = day + "-" + month + "-" + year;
		return mydate;
	}

	public String formatDecimal(double number) {

		// DecimalFormat nf = new DecimalFormat("###,###,###,##0.00");
		DecimalFormat nf = new DecimalFormat("###,###,###,##0");
		String formatted = nf.format(number);

		return formatted;
	}

	class ViewHolder {
		LinearLayout reportitem;
		TextView txtstt;
		TextView txtDate;
		TextView txtTotalreport;
		TextView txtTotalValue;
		TextView txtAverage;

	}

}
*/