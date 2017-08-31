package com.example.halle.naturallanguageapi.View;

import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.halle.naturallanguageapi.R;
import com.example.halle.naturallanguageapi.RecyclerView.Adapter;
import com.example.halle.naturallanguageapi.RecyclerView.FeedItem;
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
import java.util.ArrayList;
import java.util.List;

public class NaturalLanguage extends AppCompatActivity {

    String API_KEY = "AIzaSyDfKOkV9s0UsfPUf3wzMBuZfO0qFVaYt14";
    private ListView listView;

    private List<FeedItem> feedItemList = new ArrayList<FeedItem>();
    private RecyclerView mRecyclerView;
    private Adapter adapter;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_natural_language);


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        adapter = new Adapter(NaturalLanguage.this, feedItemList);
        mRecyclerView.setAdapter(adapter);


        ImageButton helper = (ImageButton) findViewById(R.id.btnHelper);
        helper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHelper();
            }
        });

        Button analyzeButton = (Button)findViewById(R.id.btnInput);
        analyzeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                // More code here
                getResponse();

            }

        });

    }



    public void getResponse(){



        final CloudNaturalLanguage naturalLanguageService =
                new CloudNaturalLanguage.Builder(
                        AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(),
                        null
                ).setCloudNaturalLanguageRequestInitializer(
                        new CloudNaturalLanguageRequestInitializer(API_KEY)
                ).build();

        String transcript = ((TextView)findViewById(R.id.etFrase)).getText().toString();


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

                    runOnUiThread(new Runnable() {



                        @Override
                        public void run() {
                            String entities = "";
                            for(Token entity:entityList) {
                                entities += "\n" + entity.getPartOfSpeech().getTag();

                                parseResult(entity.getLemma(),entity.getPartOfSpeech().getTag());

                            }
                            System.out.println("Result: " + entities);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        });


    }

    private void parseResult(String lemma, String tag) {
        progressBar.setVisibility(View.GONE);


        adapter = new Adapter(NaturalLanguage.this, feedItemList);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        FeedItem item = new FeedItem();

        item.setPalavra(lemma);
        item.setTag(tag);

        feedItemList.add(item);

    }



    public void showHelper(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Significado das abreviações:");
        builder.setMessage("DESCONHECIDO = Desconhecido" + "\n" +
                "ADJ = Adjetivo\n" +
                "ADP = Adposition (preposição e postposição)" + "\n" +
                "ADV = Adverb" + "\n" +
                "CONJ = Conjunção" + "\n" +
                "DET = Determiner" + "\n" +
                "NOUN = Substantivo (comum e apropriado)" + "\n" +
                "NUM = Número cardinal " + "\n" +
                "PRON = Pronom" + "\n" +
                "PRT = Partícula ou outra palavra de função" + "\n" +
                "PUNCT = Pontuação " + "\n" +
                "VERB = Verbo (todos os tempos e modos)" + "\n" +
                "X = Outros: palavras estrangeiras, erros de digitação, abreviaturas" + "\n" +
                "AFFIX =  Afixo" + "\n" +
        "");
        builder.setPositiveButton("OK",null);
        builder.create();
        builder.show();
    }

}
