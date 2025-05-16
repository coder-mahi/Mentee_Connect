package com.mahesh.mentee_connect.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
public class MentorEngagementStats {
    private List<MentorStats> mentorStats;

    public List<MentorStats> getMentorStats() {
        return mentorStats;
    }

    public void setMentorStats(List<MentorStats> mentorStats) {
        this.mentorStats = mentorStats;
    }
}