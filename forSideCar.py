import datetime
import httplib2
from twisted.web import server, resource
from twisted.internet import reactor, endpoints


class Health(resource.Resource):
    isLeaf = True

    def render_GET(self, request):
        request.responseHeaders.addRawHeader("content-type", "application/json")
        print(datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S') + ' SideCar健康检查')
        return '{"status":"UP"}'.encode('utf-8')


root = Health()
root.putChild('health', Health())
endpoints.serverFromString(reactor, "tcp:port=5680").listen(server.Site(root))
reactor.run()
