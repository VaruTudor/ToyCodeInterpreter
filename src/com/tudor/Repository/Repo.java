package com.tudor.Repository;

import com.tudor.Model.ProgramState;

import java.io.IOException;
import java.util.List;

public interface Repo {
    void addProgramState(ProgramState newProgramState);
    void setLogFilePath(String filePath);

    void logProgramStateExecution(ProgramState programState);
    List<ProgramState> getProgramStateList();
    void setProgramStateList(List<ProgramState> newProgramStateList);
    public ProgramState getProgramStateHavingId(int id);
}
