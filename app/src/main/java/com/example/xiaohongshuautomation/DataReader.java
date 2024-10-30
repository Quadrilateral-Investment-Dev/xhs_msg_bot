package com.example.xiaohongshuautomation;

import android.content.Context;
import android.content.res.AssetManager;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DataReader {

    private Context context;

    public DataReader(Context context) {
        this.context = context;
    }

    public Map<String, String> fetchXhsIdUserIdMap() {
        Map<String, String> xhsIdUserIdMap = new LinkedHashMap<>();
        AssetManager assetManager = context.getAssets();

        try (InputStream is = assetManager.open("potential_landlords.csv");
             InputStreamReader isr = new InputStreamReader(is);
             CSVParser parser = new CSVParser(isr, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord record : parser) {
                String xhsId = record.get("xhs_id").trim();
                String userId = record.get("user_id").trim();
                xhsIdUserIdMap.put(xhsId, userId); // Add the pair to the map
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xhsIdUserIdMap;
    }
}
