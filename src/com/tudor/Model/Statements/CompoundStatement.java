package com.tudor.Model.Statements;

import com.tudor.Model.ADTs.IDict;
import com.tudor.Model.ADTs.IStack;
import com.tudor.Model.ProgramState;
import com.tudor.Model.Types.Type;

public class CompoundStatement implements Statement{
    private final Statement firstStatement;
    private final Statement secondStatement;

    public CompoundStatement(Statement firstStatement, Statement secondStatement) {
        this.firstStatement = firstStatement;
        this.secondStatement = secondStatement;
    }

    @Override
    public String toString(){
        return "Compound(" + firstStatement.toString() + "," + secondStatement.toString() + ")  ";
    }

    @Override
    public String toStringAsCode() {
        return firstStatement.toStringAsCode() + "\n" + secondStatement.toStringAsCode() ;
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnvironment) {
        return secondStatement.typecheck(firstStatement.typecheck(typeEnvironment));
    }


    /**
     * secondStatement is pushed onto the state's stackOfStatements followed by firstStatement.
     * @param state (type ProgramState)
     * @return null
     */
    @Override
    public ProgramState execute(ProgramState state) {
        IStack<Statement> stack = state.getStack();
        stack.push(secondStatement);
        stack.push(firstStatement);
        return null;
    }

}
