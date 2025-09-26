package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;


public class fTemplate extends AstOperandNod {
	private final boolean amExtender;
	private final fTemplateBody templateBody;

	public fTemplate(boolean amExtender, fTemplateBody body) {
		this.amExtender = amExtender;
		this.templateBody = body;
	}

	public boolean isAmExtender() {
		return amExtender;
	}

	public fTemplateBody getTemplateBody() {
		return templateBody;
	}

	@Override
	public void accept(AstNodVisitor v) {
		v.visit(this);
	}

	@Override
	public String toString() {
		return "fTemplateOpt{" +
				"amExtender=" + amExtender +
				", templateBody=" + templateBody +
				'}';
	}
}
