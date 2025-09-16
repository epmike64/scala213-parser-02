package com.flint.compiler.frontend.parse;

import com.flint.compiler.frontend.ast.nodes.AstNod;
import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;
import com.flint.compiler.frontend.ast.nodes.leaves.node.fImport;
import com.flint.compiler.frontend.ast.nodes.leaves.node.fPackage;

import java.util.ArrayList;
import java.util.List;

public class fCompilationUnit extends AstOperandNod {

	private List<fPackage> packages;
	private List<fImport> imports = new ArrayList<>();
	private final List<AstNod> statements = new ArrayList<>();

	public void setPackages(List<fPackage> packages) {
		assert packages != null && packages.size() > 0;
		this.packages = packages;
	}

	public void addImport(fImport importNod) {
		assert importNod != null;
		imports.add(importNod);
	}

	public void addStatement(AstNod statementNod) {
		assert statementNod != null;
		statements.add(statementNod);
	}

	public List<fPackage> getPackages() {
		return packages;
	}

	public List<fImport> getImports() {
		return imports;
	}

	public List<AstNod> getStatements() {
		return statements;
	}

	@Override
	public void accept(AstNodVisitor visitor) {
		visitor.visit(this);
	}
}
