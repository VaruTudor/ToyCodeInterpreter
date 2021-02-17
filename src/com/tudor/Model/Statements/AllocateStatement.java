package com.tudor.Model.Statements;

import com.tudor.Exceptions.TypeExceptions.TypeMismatch;
import com.tudor.Exceptions.TypeExceptions.ValueNotRefType;
import com.tudor.Exceptions.VariableNotDeclared;
import com.tudor.Model.ADTs.IDict;
import com.tudor.Model.ADTs.IHeap;
import com.tudor.Model.Expressions.Expression;
import com.tudor.Model.ProgramState;
import com.tudor.Model.Types.RefType;
import com.tudor.Model.Types.Type;
import com.tudor.Model.Values.RefValue;
import com.tudor.Model.Values.Value;

public class AllocateStatement implements Statement{
    String variableId;
    Expression expression;

    public AllocateStatement(String variableId, Expression expression) {
        this.variableId = variableId;
        this.expression = expression;
    }

    /**
     * Creates a new entry on state's heap having
     * as key the nextFreeLocation(field in Heap, type(int))
     * and as value the result of expression.
     * After that, it updates the state's SymbolTable entry with key variableId
     * with a new value of type RefValue(with the updated address (nextFreeLocation), and the same type as before)
     * @param state (type ProgramState)
     * @return null
     * @throws TypeMismatch expression.evaluate has a different Type then locationType of variableId(is state's symbolTable) Value
     * @throws ValueNotRefType variableId(is state's symbolTable) Value is not RefValue(which has RefType)
     * @throws VariableNotDeclared variableId not defined as key in state's SymbolTable
     */
    @Override
    public ProgramState execute(ProgramState state) {
        IDict<String, Value> symTable = state.getSymTable();
        IHeap<Integer, Value> heap = state.getHeap();

        if (symTable.isDefined(variableId) ) {
            if (symTable.lookup(variableId).getType() instanceof RefType) {
                Value result = expression.evaluate(symTable, heap);
                Type locationType = ((RefValue)symTable.lookup(variableId)).getLocationType();
                // compare result type to locationType (from the value associated to variableId)
                if (result.getType().equals(locationType)){
                    int nextFreeLocation = heap.getNextFreeLocation();
                    heap.add(nextFreeLocation, result);
                    symTable.update(variableId, new RefValue(nextFreeLocation, locationType));
                } else {
                    throw new TypeMismatch();
                }
            } else {
                throw new ValueNotRefType();
            }
        }else{
            throw new VariableNotDeclared();
        }

        return null;
    }

    @Override
    public String toStringAsCode() {
        return "new(" + variableId + "," + expression.toString() + ");";
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnvironment) {
        Type typeVariableId = typeEnvironment.lookup(variableId);
        Type typeExpression = expression.typecheck(typeEnvironment);
        if(typeVariableId.equals(new RefType(typeExpression))){
            return typeEnvironment;
        }else{
            throw new TypeMismatch();
        }
    }

    @Override
    public String toString() {
        return "new(" + variableId + "," + expression.toString() + ")  ";
    }
}
