package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.parse.lex.token.type.fNamedToken;

import java.util.List;

public class fTypeDef extends AstOperandNod {
	private final fNamedToken name;
	private List<fVariantTypeParam> typeParams;
	private fType assignedType;
	public fTypeDef(fNamedToken name) {
		this.name = name;
	}
	public void setTypeParams(List<fVariantTypeParam> typeParams) {
		this.typeParams = typeParams;
	}
	public void setAssignedType(fType assignedType) {
		this.assignedType = assignedType;
	}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}

	@Override
	public String toString() {
		return "fTypeDef{" +
				"name=" + name +
				", typeParams=" + typeParams +
				", assignedType=" + assignedType +
				'}';
	}

	public String getName() {
		return name.getName();
	}

	public List<fVariantTypeParam> getTypeParams() {
		return typeParams;
	}
	public fType getAssignedType() {
		return assignedType;
	}

}
