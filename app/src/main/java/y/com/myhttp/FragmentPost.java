package y.com.myhttp;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPost extends Fragment {


    private TextView mStreamText;
    private ProgressBar mProgressBar;


    public FragmentPost() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mStreamText = view.findViewById(R.id.tv_stream);
        mProgressBar = view.findViewById(R.id.v_progress_bar);

        performPost();
    }

    @SuppressLint("StaticFieldLeak")
    private void performPost() {
        new AsyncTask<Void, Void, Void>() {
            StringBuffer sb = new StringBuffer();

            @Override
            protected void onPreExecute() {
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Void doInBackground(Void... voids) {


                HttpURLConnection urlConnection = null;

                try {
                    URL url = new URL("http://httpbin.org/post");


                    urlConnection = (HttpURLConnection) url.openConnection();

                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoOutput(true);

                    OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());

                    String dataOut = "name yuvi";

                    byte[] b = dataOut.getBytes(Charset.forName("UTF-8"));
                    out.write(b);
                    out.flush();
                    out.close();

                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                    BufferedReader br = new BufferedReader(new InputStreamReader(in));

                    String data;
                    while ((data = br.readLine()) != null) {
                        sb.append(data + "\n");
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                mStreamText.setText(sb.toString());
                mProgressBar.setVisibility(View.GONE);
            }
        }.execute();
    }

}
