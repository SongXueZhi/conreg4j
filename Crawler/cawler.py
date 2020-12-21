import requests
import pygal
from pygal.style import LightColorizedStyle as LCS, LightenStyle as LS
from urllib.error import URLError, HTTPError, ContentTooShortError

url = 'https://api.github.com/search/repositories?q=language:Java&sort=stars'
def download(url, num_retries=100):
    try:
        html = requests.get(url)
    except (URLError, HTTPError, ContentTooShortError) as e:
        print('Download error:', e.reason)
        html = None
        if num_retries > 0:
            return download(url, num_retries - 1)
    return html

# 创建内容对象
r = download(url)

response_dict = r.json()

print('Total repositories:', response_dict['total_count'])

repo_dicts = response_dict['items']

names, plot_dicts = [], []
for repo_dict in repo_dicts:
    names.append(repo_dict['name'])
    plot_dict = {
        'value': repo_dict['stargazers_count'],  # 显示项目热度
        'label': repo_dict['description'],  # 显示项目介绍
        'xlink': repo_dict['html_url'],  # 点击条形柱，链接项目网页
    }
    plot_dicts.append(plot_dict)

my_style = LS('#333366', base_style=LCS)
my_config = pygal.Config()
my_config.x_label_rotation = 45
my_config.show_legend = False
my_config.title_font_size = 24
my_config.label_font_size = 14
my_config.major_label_font_size = 18
my_config.truncate_label = 15
my_config.show_y_guides = False
my_config.width = 1000

chart = pygal.Bar(my_config, style=my_style)
chart.title = 'Most-Starred  Projects on GitHub'
chart.x_labels = names

chart.add('', plot_dicts)
chart.render_to_file('Java_repos2.svg')