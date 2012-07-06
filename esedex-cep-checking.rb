# Usage:
# 
# require 'esedex-cep-checking.rb'
# 
# ESedex::CEPChecking.new.check "08084530"
module ESedex
  class CEPRange
    def initialize min, max, hasOnlyPostage
      @range, @hasOnlyPostage = Range.new( formatCEP(min), formatCEP(max) ), hasOnlyPostage
    end

    def include? cep
      @range.include? formatCEP(cep)
    end

    def onlyPostage?
      @hasOnlyPostage
    end

    private
    def formatCEP cep
      cep.gsub('-', '').to_i
    end
  end

  class CEPChecking
    def initialize ceps_files='./ceps.txt'
      @ranges = []
      file = File.open(ceps_files);
      begin
        file.readlines.each do |line|
          line = line.strip.chomp
          addRange line unless line.size < 20 or line.start_with? '#'
        end
      ensure
        file.close
      end
    end

    # check method can return :no, :yes or :yes_only_postage
    def check cep
      @ranges.each do |r|
        if r.include? cep
          return r.onlyPostage? ? :yes_only_postage : :yes
        end
      end
      :no
    end

    private
    def addRange line
      lastCEP = line[line.rindex(' '), line.size].strip

      priorLastCEP = line[0, line.rindex(' ')]
      priorLastCEP = priorLastCEP[priorLastCEP.rindex(' '), priorLastCEP.size]

      @ranges << CEPRange.new( priorLastCEP, lastCEP, line.include?('*') )
    end
  end
end