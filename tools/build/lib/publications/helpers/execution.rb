require "open3"

module Execution
  def run_cmdline(name, cmdline)
    success = true
    exitstatus = 0
    Open3.popen2(cmdline) do |_stdin, stdout, wait_thr|
      stdout.each_line do |line|
        puts line
      end

      success = wait_thr.value.success?
      exitstatus = wait_thr.value.exitstatus
    end
    raise GaudiError, "#{cmdline} failed with error: #{exitstatus}" unless success
    puts "#{name} completed!"
  end
end