package crypting;

import utils.Analyzer;

public interface Analysable
{
    public Object encrypt();
    public Object decrypt();
    public Object getAllCombinations(Analyzer.Lettre[] lesLettres);
}
