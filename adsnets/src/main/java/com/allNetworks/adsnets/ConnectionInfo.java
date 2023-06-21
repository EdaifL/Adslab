package com.allNetworks.adsnets;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ConnectionInfo {
    private static final String TAG = "ConnectionInfo";
    private static final String API_URL = "http://ip-api.com/line/";

    private ConnectionInfoListener mListener;

    public interface ConnectionInfoListener {
        void onConnectionInfoReceived(ArrayList<String> result);
        void onConnectionInfoError(String message);
    }

    public ConnectionInfo(ConnectionInfoListener listener) {
        mListener = listener;
    }

    public void retrieveConnectionInfo() {
        new ConnectionInfoTask().execute();
    }

    private class ConnectionInfoTask extends AsyncTask<Void, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            ArrayList<String> result = new ArrayList<>();

            try {
                // Open HTTP connection to API URL
                URL url = new URL(API_URL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                connection.setDoInput(true);

                // Read response from HTTP connection
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                int i = 0;
                while ((line = reader.readLine()) != null ) {
                    result.add(line.trim());
                    i++;
                }
            } catch (IOException e) {
                Log.e(TAG, "Error retrieving connection info: " + e.getMessage());
                result = null;
            } finally {
                // Close HTTP connection and reader
                if (connection != null) {
                    connection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e(TAG, "Error closing reader: " + e.getMessage());
                    }
                }
            }

            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            if (result != null) {
                mListener.onConnectionInfoReceived(result);
            } else {
                mListener.onConnectionInfoError("Error retrieving connection info.");
            }
        }
    }
}