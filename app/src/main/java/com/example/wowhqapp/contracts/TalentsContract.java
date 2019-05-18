package com.example.wowhqapp.contracts;

import com.example.wowhqapp.databases.entity.Talent;
import com.example.wowhqapp.databases.entity.WowClass;
import com.example.wowhqapp.databases.entity.WowSpec;
import com.example.wowhqapp.databases.entity.WowTalents;

import java.util.List;

public interface TalentsContract {
    public interface TalentsRepository {
        List<WowClass> getWowClasses();
        List<WowSpec> getWowSpecs();
        WowTalents getWowTalents(int specId);
        WowTalents getWowTalents();

        void refresh();
    }

    public interface Presenters {

    }
}
