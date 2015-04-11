json.array!(@applies) do |apply|
  json.extract! apply, :id, :user, :jobId, :date
  json.url apply_url(apply, format: :json)
end
