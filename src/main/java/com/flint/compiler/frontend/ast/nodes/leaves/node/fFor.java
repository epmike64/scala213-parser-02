package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;

import java.util.List;

public class fFor extends AstOperandNod {
	private final List<fGenerator> generators;
	public fFor(List<fGenerator> generators) {
		this.generators = generators;
	}
}
