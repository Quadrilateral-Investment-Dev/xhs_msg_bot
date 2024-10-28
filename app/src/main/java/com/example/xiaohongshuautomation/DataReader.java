package com.example.xiaohongshuautomation;

import android.content.Context;
import android.content.res.AssetManager;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DataReader {

    private Context context;

    public DataReader(Context context) {
        this.context = context;
    }

    public List<String> fetchXhsIds() {
        List<String> xhsIds = new ArrayList<>();
        AssetManager assetManager = context.getAssets();

        try (InputStream is = assetManager.open("potential_landlords_test.csv");
             InputStreamReader isr = new InputStreamReader(is);
             CSVParser parser = new CSVParser(isr, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord record : parser) {
                xhsIds.add(record.get("xhs_id").trim()); // Read the xhs_id column by header name
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xhsIds;
    }
}
