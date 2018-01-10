package xtremecreations.surfer;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
public class CustomAdapter extends BaseAdapter{
    String [] result;
    Home context;
    LayoutInflater inflater=null;
    public CustomAdapter(Home mainActivity, String[] search) {
        result=search;
        context=mainActivity;
        if(getCount()==0){context.divider1.setVisibility(View.GONE);}
        else{context.divider1.setVisibility(View.VISIBLE);}
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {return result.length;}
    @Override
    public Object getItem(int position) {return position;}
    @Override
    public long getItemId(int position) {return position;}
    public class Holder {RelativeLayout listpane;TextView search_tag;ImageView search_copy;}
    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.search_list, null);
        holder.search_tag=(TextView) rowView.findViewById(R.id.search_tag);
        holder.listpane=(RelativeLayout) rowView.findViewById(R.id.list_item);
        holder.search_copy=(ImageView) rowView.findViewById(R.id.search_copy);
        holder.search_tag.setText(result[position]);
        holder.search_copy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v){context.addbar.setText(result[position]);}
        });
        holder.listpane.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                context.hideAddHover();
                new Handler().postDelayed(new Runnable() {@Override public void run(){
                    if(! context.addbar.getText().toString().equals("")){context.surf.loadUrl("https://www.google.co.in/m?q="+result[position]);}}},250);
                String[]Null={};context.searchlist.setAdapter(new CustomAdapter(context,Null));
            }
        });
        return rowView;
    }
}