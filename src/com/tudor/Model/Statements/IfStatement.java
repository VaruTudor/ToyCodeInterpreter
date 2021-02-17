package com.tudor.Model.Statements;

import com.tudor.Exceptions.TypeExceptions.ConditionNotBoolType;
import com.tudor.Exceptions.TypeExceptions.TypeMismatch;
import com.tudor.Model.ADTs.IDict;
import com.tudor.Model.ADTs.IHeap;
import com.tudor.Model.ADTs.IStack;
import com.tudor.Model.ADTs.MyDict;
import com.tudor.Model.Expressions.Expression;
import com.tudor.Model.ProgramState;
import com.tudor.Model.Types.BoolType;
import com.tudor.Model.Types.Type;
import com.tudor.Model.Values.BoolValue;
import com.tudor.Model.Values.Value;

public class IfStatement implements Statement{
    Expression expression;
    Statement thenStatement;
    Statement elseStatement;

    public IfStatement(Expression expression, Statement thenStatement, Statement elseStatement) {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public String toString() {
        return "If("
                + expression.toString() + ") "
                + " (" + thenStatement.toString() + ")"
                + " else (" + elseStatement.toString() + ")  ";
    }


    @Override
    public String toStringAsCode() {
        return "If("
                + expression.toString() + "){ "
                + "\n\t" + thenStatement.toStringAsCode()
                + "\n}Else{ \n\t" + elseStatement.toStringAsCode() + "\n}";
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String,Type> typeEnvironment) {
        Type typeExpression = expression.typecheck(typeEnvironment);
        if(typeExpression.equals(new BoolType())){
            if(typeEnvironment instanceof MyDict){
                thenStatement.typecheck(new MyDict<>((MyDict<String, Type>) typeEnvironment));
                elseStatement.typecheck(new MyDict<>((MyDict<String, Type>) typeEnvironment));
                return typeEnvironment;
            }else {
                throw new TypeMismatch();
            }
        }else {
            throw new ConditionNotBoolType();
        }
    }

    /**
     * If expression is evaluated as true then thenStatement is pushed onto the state's Stack
     * otherwise elseStatement is pushed.
     * @param state (type ProgramState)
     * @return null
     * @throws ConditionNotBoolType expression.evaluate is not BoolValue(which has BoolType)
     */
    @Override
    public ProgramState execute(ProgramState state)  {
        IStack<Statement> stack = state.getStack();
        IDict<String, Value> symbolTable = state.getSymTable();
        IHeap<Integer, Value> heap = state.getHeap();

        Value condition = expression.evaluate(symbolTable,heap);
        if (!condition.getType().equals(new BoolType())){
            throw new ConditionNotBoolType();
        }

        BoolValue boolCondition = (BoolValue) condition;
        if(boolCondition.getValue())
        {
            //true
            stack.push(thenStatement);
        } else{
            //false
            stack.push(elseStatement);
        }

        return null;
    }
}
