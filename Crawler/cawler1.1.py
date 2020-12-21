import requests
import operator
import json
import time

def start_requests(url):
    print('getting', url)
    return requests.get(url, auth = ('SongXueZhi', '5465071389feb2cb6860f04da78c54ba5ac235e2'))

def get_info(rep):
    data = rep.json()
    repo_dicts = data['items']
    for dict in repo_dicts:
        yield {
            'project_name': dict['full_name'],
            'project_url': dict['html_url'],
            'project_api_url': dict['url'],
            'star_count': dict['stargazers_count']
        }

def get_all():
    baseurl = 'https://api.github.com/search/repositories?q=language:Java&sort=stars?page={}'
    for i in range(1, 3):
        url = baseurl.format(i)
        r = start_requests(url)
        yield from get_info(r)

def main():
    start = time.time()
    data = list(get_all())
    data.sort(key=operator.itemgetter('star_count'), reverse=True)
    s = json.dumps(data, ensure_ascii=False, indent=4)
    with open('github.json', 'w', encoding='utf-8') as f:
        f.write(s)
    end = time.time()
    print(end - start)

if __name__ == '__main__':
    main()