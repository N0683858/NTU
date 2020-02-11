package com.example.ntustores;

import android.os.AsyncTask;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;

public class DatabaseCommunication {
    
    private final String m_dbPostUrl = "http://stockmanagersystem.gearhostpreview.com/dbPost.php?nNumber=a&image=b";

    public DatabaseCommunication()
    {
    }


    public void GenericUpload(BaseCallback cb)
    {
        new UploadData().execute(cb);
    }

    private class UploadData extends AsyncTask<BaseCallback, String, String>
    {
        @Override
        protected String doInBackground(BaseCallback... params)
        {
            try
            {
                URLConnection connection = new URL(m_dbPostUrl).openConnection();
                connection.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write("request="+params[0].GetMessage());
                writer.flush();
                InputStream res = connection.getInputStream();
                String result = IOUtils.toString(res, StandardCharsets.UTF_8);
                writer.close();

                params[0].SetMessage(result);
            }
            catch (Exception e)
            {
                params[0].SetMessage(e.getMessage());
            }

            try
            {
                params[0].call();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return "Executed";
        }
    }
}
