package com.github.gvidas333.eventsync.service;

import com.github.gvidas333.eventsync.model.Sentiment;
import org.springframework.stereotype.Service;

@Service
public class SentimentAnalysisService {

    public Sentiment analyzeSentiment(String text) {
        //TODO: implement hugging face call
        return Sentiment.NEUTRAL;
    }
}
