package com.hacareem.finaldestination.repository.base;

import com.hacareem.finaldestination.domain.RideSuggestion;
import com.hacareem.finaldestination.search.params.RideSuggestionParams;

/**
 * Created by waqas on 4/30/17.
 */
public interface RideSuggestionRepository {
    RideSuggestion get(RideSuggestionParams params);
    RideSuggestion save(RideSuggestion rideSuggestion);
    RideSuggestion update(RideSuggestion rideSuggestion);
}
