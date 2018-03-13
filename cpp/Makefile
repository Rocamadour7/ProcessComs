CXX=			g++ $(CXXFLAGS)

ECHO-SERVER=		protocol.o requestprotocol.o ResponseHandler.o server_impl.o server.o str_utils.o updateprotocol.o
ECHO-CLIENT=		client_impl.o client.o
OBJS =			$(ECHO-SERVER) $(ECHO-CLIENT)

LIBS=

CXXFLAGS= -g

all:	server client

server:$(ECHO-SERVER)
	$(CXX) -o server $(ECHO-SERVER) $(LIBS)

client:$(ECHO-CLIENT)
	$(CXX) -o client $(ECHO-CLIENT) $(LIBS)

clean:
	rm -f $(OBJS) $(OBJS:.o=.d)

realclean:
	rm -f $(OBJS) $(OBJS:.o=.d) server client

%.o : %.cc
	$(CXX) -c -o $@ -MD -MP ${CPPFLAGS} ${CXXFLAGS} $<

-include $(OBJS:.o=.d)
