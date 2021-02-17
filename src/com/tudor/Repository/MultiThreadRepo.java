package com.tudor.Repository;

import com.tudor.Model.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MultiThreadRepo implements Repo {
    private List<ProgramState> programStates;
    private String logFilePath;

    @Override
    public void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }


    public MultiThreadRepo() {
        this.programStates = new ArrayList<>();
    }

    @Override
    public void addProgramState(ProgramState newProgramState) {
        programStates.add(newProgramState);
    }

    @Override
    public List<ProgramState> getProgramStateList() {
        return programStates;
    }

    @Override
    public void setProgramStateList(List<ProgramState> programStates) {
        this.programStates = programStates;
    }

    @Override
    public ProgramState getProgramStateHavingId(int id) {
        for(ProgramState programState : programStates)
            if(programState.getId() == id)
                return programState;
        return null;
    }

    @Override
    public void logProgramStateExecution(ProgramState programState) {
        PrintWriter logFile;
        try {
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        } catch (IOException e) {
            throw new RuntimeException("cannot log program state\n");
        }
        logFile.write(programState.toString());
        logFile.close();
    }
}
