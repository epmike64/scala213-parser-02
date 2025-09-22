package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;

import java.util.Optional;

public class fTemplate extends AstOperandNod {
	private final boolean amExtender;
	private final Optional<fTemplateBody> templateBody;

	public fTemplate(boolean amExtender, Optional<fTemplateBody> body) {
		this.amExtender = amExtender;
		assert body != null : "body cannot be null";
		this.templateBody = body;
	}

	public boolean isAmExtender() {
		return amExtender;
	}

	public Optional<fTemplateBody> getTemplateBody() {
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
