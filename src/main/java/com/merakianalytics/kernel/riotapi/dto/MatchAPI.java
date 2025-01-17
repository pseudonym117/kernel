package com.merakianalytics.kernel.riotapi.dto;

import java.net.HttpURLConnection;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;

import org.jboss.resteasy.annotations.GZIP;

import com.google.common.collect.ImmutableMap;
import com.merakianalytics.kernel.riotapi.RiotAPIService;
import com.merakianalytics.orianna.types.common.Platform;
import com.merakianalytics.orianna.types.dto.match.Match;
import com.merakianalytics.orianna.types.dto.match.MatchTimeline;
import com.merakianalytics.orianna.types.dto.match.Matchlist;
import com.merakianalytics.orianna.types.dto.match.TournamentMatches;

import io.swagger.annotations.Api;

/**
 * The Match API proxy for the Riot API
 *
 * @see https://developer.riotgames.com/api-methods/#match-v4
 */
@Path("/match/v4")
@Api("Match API")
@GZIP
public class MatchAPI extends RiotAPIService {
    /**
     * /lol/match/v4/matches/{matchId}
     *
     * @see https://developer.riotgames.com/api-methods/#match-v4/GET_getMatch
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param matchId
     *        the match's id
     * @return {@link com.merakianalytics.orianna.types.dto.match.Match}
     */
    @Path("/matches/{matchId}")
    @GET
    public Match getMatch(@QueryParam("platform") final String platformTag, @PathParam("matchId") final long matchId) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("matchId", matchId)
            .build();

        return context.getPipeline().get(Match.class, query);
    }

    /**
     * /lol/match/v4/matches/{matchId}/by-tournament-code/{tournamentCode}
     *
     * @see https://developer.riotgames.com/api-methods/#match-v4/GET_getMatchByTournamentCode
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param matchId
     *        the match's id
     * @param tournamentCode
     *        the tournament code
     * @return {@link com.merakianalytics.orianna.types.dto.match.Match}
     */
    @Path("/matches/{matchId}/by-tournament-code/{tournamentCode}")
    @GET
    public Match getMatchByTournamentCode(@QueryParam("platform") final String platformTag, @PathParam("matchId") final long matchId,
        @PathParam("tournamentCode") final String tournamentCode) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("matchId", matchId)
            .put("tournamentCode", tournamentCode)
            .build();

        return context.getPipeline().get(Match.class, query);
    }

    /**
     * /lol/match/v4/matches/by-tournament-code/{tournamentCode}/ids
     *
     * @see https://developer.riotgames.com/api-methods/#match-v4/GET_getMatchIdsByTournamentCode
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param tournamentCode
     *        the tournament code
     * @return {@link com.merakianalytics.orianna.types.dto.match.TournamentMatches}
     */
    @Path("/matches/by-tournament-code/{tournamentCode}/ids")
    @GET
    public TournamentMatches getMatchIdsByTournamentCode(@QueryParam("platform") final String platformTag,
        @PathParam("tournamentCode") final String tournamentCode) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("tournamentCode", tournamentCode)
            .build();

        return context.getPipeline().get(TournamentMatches.class, query);
    }

    /**
     * /lol/match/v4/matchlists/by-account/{encryptedAccountId}
     *
     * @see https://developer.riotgames.com/api-methods/#match-v4/GET_getMatchlist
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param encryptedAccountId
     *        the account's encrypted id
     * @param queue
     *        the ids of the queues
     * @param endTime
     *        the latest time for the query
     * @param beginIndex
     *        the first result to return
     * @param beginTime
     *        the earliest time for the query
     * @param season
     *        the ids of the seasons
     * @param champion
     *        the ids of the champions
     * @param endIndex
     *        the last result to return
     * @return {@link com.merakianalytics.orianna.types.dto.match.Matchlist}
     */
    @Path("/matchlists/by-account/{encryptedAccountId}")
    @GET
    public Matchlist getMatchlist(@QueryParam("platform") final String platformTag, @PathParam("encryptedAccountId") final String encryptedAccountId,
        @QueryParam("queue") final Set<Integer> queue, @QueryParam("endTime") @DefaultValue("-1") final long endTime,
        @QueryParam("beginIndex") @DefaultValue("-1") final int beginIndex, @QueryParam("beginTime") @DefaultValue("-1") final long beginTime,
        @QueryParam("season") final Set<Integer> season, @QueryParam("champion") final Set<Integer> champion,
        @QueryParam("endIndex") @DefaultValue("-1") final int endIndex) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();

        final ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        builder.put("platform", platform);
        builder.put("accountId", encryptedAccountId);

        if(queue != null) {
            builder.put("queues", queue);
        }

        if(endTime != -1L) {
            builder.put("endTime", endTime);
        }

        if(beginIndex != -1) {
            builder.put("beginIndex", beginIndex);
        }

        if(beginTime != -1L) {
            builder.put("beginTime", beginTime);
        }

        if(season != null) {
            builder.put("seasons", season);
        }

        if(champion != null) {
            builder.put("champions", champion);
        }

        if(endIndex != -1) {
            builder.put("endIndex", endIndex);
        }

        return context.getPipeline().get(Matchlist.class, builder.build());
    }

    /**
     * /lol/match/v4/timelines/by-match/{matchId}
     *
     * @see https://developer.riotgames.com/api-methods/#match-v4/GET_getMatchTimeline
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param matchId
     *        the match's id
     * @return {@link com.merakianalytics.orianna.types.dto.match.MatchTimeline}
     */
    @Path("/timelines/by-match/{matchId}")
    @GET
    public MatchTimeline getMatchTimeline(@QueryParam("platform") final String platformTag, @PathParam("matchId") final long matchId) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();
        if(platform == null) {
            throw new WebApplicationException(platformTag + " is not a valid platform!", HttpURLConnection.HTTP_BAD_REQUEST);
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("matchId", matchId)
            .build();

        return context.getPipeline().get(MatchTimeline.class, query);
    }
}
