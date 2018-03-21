package com.example.halle.naturallanguageapi.View;

import android.os.AsyncTask;
import android.widget.TextView;

import com.example.halle.naturallanguageapi.R;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.services.language.v1beta2.CloudNaturalLanguage;
import com.google.api.services.language.v1beta2.CloudNaturalLanguageRequestInitializer;
import com.google.api.services.language.v1beta2.model.AnnotateTextRequest;
import com.google.api.services.language.v1beta2.model.AnnotateTextResponse;
import com.google.api.services.language.v1beta2.model.Document;
import com.google.api.services.language.v1beta2.model.Features;
import com.google.api.services.language.v1beta2.model.Token;

import java.io.IOException;
import java.util.List;

/**
 * Created by halle on 30/08/2017.
 */

public class ResponseApi {
    String API_KEY = "YOUR KEY HERE";

    String processor = null;


    public void getResponse(String transcript){



        final CloudNaturalLanguage naturalLanguageService =
                new CloudNaturalLanguage.Builder(
                        AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(),
                        null
                ).setCloudNaturalLanguageRequestInitializer(
                        new CloudNaturalLanguageRequestInitializer(API_KEY)
                ).build();




        Document document = new Document();
        document.setType("PLAIN_TEXT");
        document.setLanguage("pt-BR");
        document.setContent(transcript);

        Features features = new Features();
        features.setExtractEntities(true);
        features.setExtractDocumentSentiment(true);
        features.setExtractSyntax(true);

        final AnnotateTextRequest request = new AnnotateTextRequest();
        request.setDocument(document);
        request.setFeatures(features);


        AsyncTask.execute(new Runnable() {



            @Override
            public void run() {
                try {
                    AnnotateTextResponse response =
                            naturalLanguageService.documents()
                                    .annotateText(request).execute();

                    final List<Token> entityList = response.getTokens();
                    final float sentiment = response.getDocumentSentiment().getScore();

                    new Runnable() {

                        @Override
                        public void run() {
                            String entities = "";
                            for(Token entity:entityList) {
                                entities += "\n" + entity.getPartOfSpeech().getTag();

                                processor += "" + entity.getLemma() + "" + "" + entity.getPartOfSpeech().getTag();
                            }
                            System.out.println("Result: " + processor);
                        }
                    };
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
    }
}
