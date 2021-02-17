package com.tudor.Model.Statements;

import com.tudor.Exceptions.TypeExceptions.TypeMismatch;
import com.tudor.Exceptions.VariableNotDeclared;
import com.tudor.Model.ADTs.IDict;
import com.tudor.Model.ADTs.IHeap;
import com.tudor.Model.Expressions.Expression;
import com.tudor.Model.ProgramState;
import com.tudor.Model.Types.Type;
import com.tudor.Model.Values.Value;

public class AssignmentStatement implements Statement{
    String variableId;
    Expression expression;

    public AssignmentStatement(String v, Expression valueExpression) {
        this.variableId = v;
        this.expression = valueExpression;
    }

    @Override
    public String toString(){
        return variableId + "=" +expression.toString() + "  ";
    }

    @Override
    public String toStringAsCode() {
        return variableId + "=" +expression.toString()+ ";";
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnvironment) {
        Type typeVariableId = typeEnvironment.lookup(variableId);
        Type typeExpression = expression.typecheck(typeEnvironment);
        if(typeVariableId.equals(typeExpression)){
            return typeEnvironment;
        }else{
            throw new TypeMismatch();
        }
    }

    /**
     * It assigns a Value to a key from the state's symbolTable
     * @param state (type ProgramState)
     * @return null
     * @throws TypeMismatch key(from the state's symbolTable) Type is not the same as Value(returned by expression evaluation) Type
     * @throws VariableNotDeclared if variableId does not exist as a key in state's symbolTable
     */
    @Override
    public ProgramState execute(ProgramState state)  {
        IDict<String, Value> symTable = state.getSymTable();
        IHeap<Integer, Value> heap = state.getHeap();

        if (symTable.isDefined(variableId) ){
            Value value = expression.evaluate(symTable,heap);
            Type type = (symTable.lookup(variableId).getType() );
            if (value.getType().equals(type)){
                symTable.update(variableId, value);
            } else{
                throw new TypeMismatch();
            }
        } else{
            throw new VariableNotDeclared();
        }

        return null;
    }


}
