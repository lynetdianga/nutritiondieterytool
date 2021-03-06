package studios.sperry.healthpile.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import studios.sperry.healthpile.R;
import studios.sperry.healthpile.activities.SingleDiseaseInfo;
import studios.sperry.healthpile.models.Disease;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Martin Mbae on 03,July,2020.
 */
public class DiseasesAdapter extends RecyclerView.Adapter<DiseasesAdapter.ViewHolder> {

    private Context context;
    private List<Disease> diseaseArrayList;

    public DiseasesAdapter(Context context, List<Disease> diseaseArrayList) {
        this.context = context;
        this.diseaseArrayList = diseaseArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(context).inflate(R.layout.row_disease,parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Disease disease = diseaseArrayList.get(position);
//        holder.diseaseIcon.setImageDrawable(disease.getDiseaseIcon());
        holder.diseaseName.setText(disease.getName());
        holder.diseaseDescription.setText(disease.getDescription());

        Picasso.get()
                .load(disease.getBaseUrl())
                .error(context.getDrawable(R.drawable.notfound))
                .placeholder(context.getDrawable(R.drawable.placeholder))
                .into(holder.diseaseIcon);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String fileName = "SomeName.png";
//
//                    Bitmap bitmap = ((BitmapDrawable) disease.getDiseaseIcon()).getBitmap();
//                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//                    byte[] b = baos.toByteArray();
//
//                FileOutputStream fileOutStream;
//                try {
//                    fileOutStream = context.openFileOutput(fileName, MODE_PRIVATE);
//                    fileOutStream.write(b);
//                    fileOutStream.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

                    Intent singleIntent = new Intent(context, SingleDiseaseInfo.class);
                    singleIntent.putExtra("diseaseName", disease.getName());
                    singleIntent.putExtra("diseaseDescription", disease.getDescription());
                    singleIntent.putExtra("diseasePrevention", disease.getPrevention());
                    singleIntent.putExtra("diseaseNutrition", disease.getNutrition());
                    singleIntent.putExtra("diseaseIcon", disease.getBaseUrl());
                    context.startActivity(singleIntent);

            }
        });

    }


    @Override
    public int getItemCount() {
        return diseaseArrayList.size();
    }

    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap, int quality) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
        byte[] byteFormat = stream.toByteArray();

        return Base64.encodeToString(byteFormat, Base64.NO_WRAP);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView diseaseIcon;
        private TextView diseaseName, diseaseDescription;
        private View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;

            diseaseIcon = view.findViewById(R.id.diseaseIcon);
            diseaseName = view.findViewById(R.id.diseaseName);
            diseaseDescription = view.findViewById(R.id.diseaseDescription);

        }
    }


}
