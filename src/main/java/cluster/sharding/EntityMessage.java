package cluster.sharding;

import akka.cluster.sharding.ShardRegion;

import java.io.Serializable;

class EntityMessage {
    static class Command implements Serializable {
        final Entity entity;

        Command(Entity entity) {
            this.entity = entity;
        }

        @Override
        public String toString() {
            return String.format("%s[%s]", getClass().getSimpleName(), entity);
        }
    }

    static class CommandAck implements Serializable {
        final String action;
        final Entity entity;

        CommandAck(String action, Entity entity) {
            this.action = action;
            this.entity = entity;
        }

        @Override
        public String toString() {
            return String.format("%s[%s, %s]", getClass().getSimpleName(), action, entity);
        }
    }

    static class Query implements Serializable {
        final Entity.Id id;

        Query(Entity.Id id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return String.format("%s[%s]", getClass().getSimpleName(), id);
        }
    }

    static class QueryAck implements Serializable {
        final Entity entity;

        QueryAck(Entity entity) {
            this.entity = entity;
        }

        @Override
        public String toString() {
            return String.format("%s[%s]", getClass().getSimpleName(), entity);
        }
    }

    static class QueryAckNotFound implements Serializable {
        final Entity.Id id;

        QueryAckNotFound(Entity.Id id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return String.format("%s[%s]", getClass().getSimpleName(), id);
        }
    }

    static ShardRegion.MessageExtractor messageExtractor() {
        final int numberOfShards = 100;

        return new ShardRegion.MessageExtractor() {
            @Override
            public String shardId(Object message) {
                return extractShardIdFromCommands(message);
            }

            @Override
            public String entityId(Object message) {
                return extractEntityIdFromCommands(message);
            }

            @Override
            public Object entityMessage(Object message) {
                return message;
            }

            private String extractShardIdFromCommands(Object message) {
                if (message instanceof Command) {
                    return ((Command) message).entity.id.id.hashCode() % numberOfShards + "";
                } else if (message instanceof Query) {
                    return ((Query) message).id.id.hashCode() % numberOfShards + "";
                } else {
                    return null;
                }
            }

            private String extractEntityIdFromCommands(Object message) {
                if (message instanceof Command) {
                    return ((Command) message).entity.id.id;
                } else if (message instanceof Query) {
                    return ((Query) message).id.id;
                } else {
                    return null;
                }
            }
        };
    }
}
