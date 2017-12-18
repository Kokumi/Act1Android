package com.debruyckere.florian.act1android.Model;

import java.util.List;

/**
 * Created by Debruyckère Florian on 12/12/2017.
 */

public interface DownloadResponse {
    /**
     * Permet l'envoi du résultat à l'adapter
     * @param result
     * resultat à envoyer
     */
    void processFinish(List<News> result);
}
