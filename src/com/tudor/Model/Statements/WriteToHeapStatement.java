package com.tudor.Model.Statements;

import com.tudor.Exceptions.AddressNotExistent;
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

public class WriteToHeapStatement implements Statement{
    String variableId;
    Expression expression;

    public WriteToHeapStatement(String variableId, Expression expression) {
        this.variableId = variableId;
        this.expression = expression;
    }

    /**
     * Updates the key address(the address of the Value associated to variableId in state's programState)
     * to a Value obtained from expression.evaluate.
     * @param state (type ProgramState)
     * @return null
     * @throws TypeMismatch expression.evaluate has a different Type then the Value found in state's heap
     * at key address(the address of the Value associated to variableId in state's programState)
     * @throws AddressNotExistent the address of the Value associated to variableId in state's programState is not a key in state's heap
     * @throws ValueNotRefType the Value associated to variableId in state's programState is not RefValue(which has RefType)
     * @throws VariableNotDeclared variableId is not a key in state's programState
     */
    @Override
    public ProgramState execute(ProgramState state) {
        IDict<String, Value> symTable = state.getSymTable();
        IHeap<Integer, Value> heap = state.getHeap();

        if (symTable.isDefined(variableId) ) {
            if (symTable.lookup(variableId).getType() instanceof RefType) {
               if(symTable.lookup(variableId) instanceof RefValue){
                   int address = ((RefValue) symTable.lookup(variableId)).getAddress();
                   if(heap.isDefined(address)){
                        Value result = expression.evaluate(symTable,heap);
                        if (heap.lookup(address).getType().equals(result.getType())){
                            heap.update(address,result);
                        }else {
                            throw new TypeMismatch();
                        }
                   }else {
                       throw new AddressNotExistent();
                   }
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
        return "wHeap(" + variableId + "," + expression.toString() + ");";
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
        return "wHeap(" + variableId + "," + expression.toString() + ")  ";
    }
}