package io.gregfreeman.cointracker.coindata;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

class UrlReader
{
    static String readUrl(String urlString) throws Exception
    {
        BufferedReader reader = null;

        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            return sb.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }
}
