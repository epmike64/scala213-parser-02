package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.parse.lex.token.type.NamedToken;

import java.util.List;

public class fTypeDef extends AstOperandNod {
	private final NamedToken name;
	private List<fVariantTypeParam> typeParams;
	private fType assignedType;
	public fTypeDef(NamedToken name) {
		this.name = name;
	}
	public void setTypeParams(List<fVariantTypeParam> typeParams) {
		this.typeParams = typeParams;
	}
	public void setAssignedType(fType assignedType) {
		this.assignedType = assignedType;
	}
}
