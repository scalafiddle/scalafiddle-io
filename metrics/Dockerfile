FROM kamon/grafana_graphite

# Create symbolic links to /data dir for all data directories
RUN ln -sf /data/metrics/whisper /opt/graphite/storage/whisper &&\
    ln -sf /data/metrics/grafana /opt/grafana/data &&\
    ln -sf /data/metrics/graphite /opt/graphite/storage/log

# Make sure the data directories exist before starting metrics tools
CMD mkdir -p /data/metrics/whisper /data/metrics/grafana /data/metrics/graphite/webapp && /usr/bin/supervisord
