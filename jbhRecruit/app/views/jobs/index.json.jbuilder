json.array!(@jobs) do |job|
  json.extract! job, :id, :title, :description, :category, :siteID, :postingDate, :fullTime, :shift
  json.url job_url(job, format: :json)
end
