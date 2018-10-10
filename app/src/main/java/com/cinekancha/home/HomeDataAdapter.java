package com.cinekancha.home;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.adapters.base.BaseViewHolder;
import com.cinekancha.article.ArticleDetailActivity;
import com.cinekancha.entities.ThumbWrapper;
import com.cinekancha.entities.ThumbnailConverter;
import com.cinekancha.entities.model.Article;
import com.cinekancha.entities.model.HomeData;
import com.cinekancha.entities.model.Movie;
import com.cinekancha.entities.model.Option;
import com.cinekancha.entities.model.PollData;
import com.cinekancha.entities.model.Trivia;
import com.cinekancha.entities.model.TrollData;
import com.cinekancha.movies.MovieActivity;
import com.cinekancha.newsGossips.NewsGossipsActivity;
import com.cinekancha.poll.PollsActivity;
import com.cinekancha.trolls.TrollListActivity;
import com.cinekancha.utils.Constants;
import com.cinekancha.utils.GlobalUtils;
import com.cinekancha.utils.PollUtil;
import com.cinekancha.utils.ViewIdGenerator;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.cinekancha.home.HomeDataWrapper.FEATURED_ARTICLE;
import static com.cinekancha.home.HomeDataWrapper.FEATURED_ARTICLE_HIGHLIGHTED;
import static com.cinekancha.home.HomeDataWrapper.FEATURED_BOX_OFFICE;
import static com.cinekancha.home.HomeDataWrapper.FEATURED_FULL_VIDEOS;
import static com.cinekancha.home.HomeDataWrapper.FEATURED_MOVIE;
import static com.cinekancha.home.HomeDataWrapper.FEATURED_PHOTO_GALLERY;
import static com.cinekancha.home.HomeDataWrapper.FEATURED_POLL;
import static com.cinekancha.home.HomeDataWrapper.FEATURED_REVIEWS;
import static com.cinekancha.home.HomeDataWrapper.FEATURED_SHOWTIMES;
import static com.cinekancha.home.HomeDataWrapper.FEATURED_TOP_STORIES;
import static com.cinekancha.home.HomeDataWrapper.FEATURED_TRENDING_VIDEOS;
import static com.cinekancha.home.HomeDataWrapper.FEATURED_TRIVIA;
import static com.cinekancha.home.HomeDataWrapper.FEATURED_TROLL;
import static com.cinekancha.home.HomeDataWrapper.HEADERS;
import static com.cinekancha.home.HomeDataWrapper.NEW_MOVIES;
import static com.cinekancha.home.HomeDataWrapper.UPCOMING_MOVIES;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class HomeDataAdapter extends BaseRecyclerAdapter<HomeItemHolder> {
    private static final String TAG = "HomeDataAdapter";
    private HomeDataWrapper mData;

    @Override
    public HomeItemHolder onCreateView(int viewType, View view) {
        switch (viewType) {
            case HEADERS:
            default: {
                return new HeaderHolder(this, view);
            }
            case NEW_MOVIES: {
                return new NewReleaseHolder(this, view);
            }
            case UPCOMING_MOVIES: {
                return new UpcomingReleaseHolder(this, view);
            }
            case FEATURED_ARTICLE_HIGHLIGHTED: {
                return new FeaturedNewsHolder(this, view);
            }
            case FEATURED_ARTICLE: {
                return new FeaturedNewsListHolder(this, view);
            }
            case FEATURED_POLL: {
                return new PollHolder(this, view);
            }
            case FEATURED_TRIVIA: {
                return new TriviaHolder(this, view);
            }
            case FEATURED_TROLL: {
                return new TrollHolder(this, view);
            }
            case FEATURED_TOP_STORIES: {
                TopStoryHolder topStoryHolder = new TopStoryHolder(this, view);
                topStoryHolder.itemView.setOnClickListener(view1 -> {
                    ArticleDetailActivity.startActivity(topStoryHolder.itemView.getContext(), mData.getItem(topStoryHolder.getAdapterPosition()));
                });
                return topStoryHolder;
            }
            case FEATURED_MOVIE: {
                return new ThumbnailViewHolder<>(
                        this,
                        view,
                        "WATCH FULL MOVIES",
                        R.layout.layout_featued_movie_new_release,
                        new ThumbnailConverter<Movie>() {
                            @Override
                            public ThumbWrapper convert(Movie data) {
                                return new ThumbWrapper(
                                        data.getFeaturedImage(),
                                        data.getName(), data.getId()
                                );
                            }
                        }
                );
            }
            case FEATURED_REVIEWS: {
                return new FeaturedReviewsHolder(this, view);
            }
            case FEATURED_BOX_OFFICE: {
                return new FeaturedBoxOfficeHolder(this, view);
            }
            case FEATURED_PHOTO_GALLERY: {
                return new FeaturedPhotosHolder(this, view);
            }
            case FEATURED_TRENDING_VIDEOS: {
                return new TrendingVideosHolder(this, view);
            }
            case FEATURED_FULL_VIDEOS: {
                return new FullVideosHolder(this, view);
            }
            case FEATURED_SHOWTIMES: {
                return new FeaturedShowtimesHolder(this, view);
            }
        }
    }

    @Override
    public int[] getLayoutsForViewType() {
        return new int[]{
                R.layout.layout_home_header,            // 0
                R.layout.layout_featured_thumbnails,    // 1
                R.layout.layout_featured_thumbnails,    // 2
                R.layout.layout_featured_articles,      // 3
                R.layout.layout_featured_thumbnails,    // 4
                R.layout.layout_featured_poll,          // 5
                R.layout.layout_featured_trivia,        // 6
                R.layout.layout_featured_trolls,        // 7
                R.layout.layout_featured_movie,         // 8
                R.layout.layout_featured_box_office,    // 9
                R.layout.layout_featured_thumbnails,    // 10
                R.layout.layout_featured_thumbnails,    // 11
                R.layout.layout_featured_thumbnails,    // 12
                R.layout.layout_featured_thumbnails,    // 13
                R.layout.layout_featured_thumbnails,    // 14
                R.layout.layout_featured_top_story,    // 15

        };
    }

    @Override
    protected void setViewOfType(BaseViewHolder baseHolder, int position, int viewType) {
        if (viewType == FEATURED_REVIEWS) {
            FeaturedReviewsHolder holder = (FeaturedReviewsHolder) baseHolder;
            holder.setMovies(mData.getItem(position));
        } else if (viewType == FEATURED_FULL_VIDEOS) {
            FullVideosHolder holder = (FullVideosHolder) baseHolder;
            holder.setVideos(mData.getItem(position));
        } else if (viewType == FEATURED_TRENDING_VIDEOS) {
            TrendingVideosHolder holder = (TrendingVideosHolder) baseHolder;
            holder.setVideos(mData.getItem(position));
        }
    }

    @Override
    protected void setViewOfTypeNine(BaseViewHolder baseHolder, int position) {
        FeaturedBoxOfficeHolder holder = (FeaturedBoxOfficeHolder) baseHolder;
        holder.setBoxOffice(mData.getItem(position));
    }

    @Override
    protected void setViewOfTypeEight(BaseViewHolder baseHolder, int position) {
        FeaturedMoviesHolder holder = (FeaturedMoviesHolder) baseHolder;
        Movie movie = mData.getItem(position);
        if (!TextUtils.isEmpty(movie.getFeaturedImage())) {
            Picasso.with(baseHolder.itemView.getContext())
                    .load(movie.getFeaturedImage())
                    .into(holder.image);
        } else {
            holder.image.setImageResource(R.drawable.placeholder_movie);
        }
    }

    @Override
    protected void setViewOfTypeSeven(BaseViewHolder baseHolder, int position) {
        TrollHolder holder = (TrollHolder) baseHolder;
        TrollData troll = mData.getItem(position);

        if (!TextUtils.isEmpty(troll.getImageUrl())) {
            String newString = troll.getImageUrl().replace("\\", "");
            Picasso.with(baseHolder.itemView.getContext())
                    .load(newString)
                    .into(holder.troll);
        }
        holder.txtViewAll.setVisibility(View.VISIBLE);
        holder.txtViewAll.setOnClickListener(view -> GlobalUtils.navigateActivity(holder.itemView.getContext(), true, TrollListActivity.class));
    }

    @Override
    protected void setViewOfTypeSix(BaseViewHolder baseHolder, int position) {
        TriviaHolder holder = (TriviaHolder) baseHolder;
        Trivia trivia = mData.getItem(position);
        holder.trivia.setText(trivia.getTrivia());
    }


    @Override
    protected void setViewOfEleven(BaseViewHolder baseHolder, int position) {
        TopStoryHolder holder = (TopStoryHolder) baseHolder;
        Article topStory = mData.getItem(position);
        holder.txtTopStories.setText(topStory.getSummary());
    }

    @Override
    protected void setViewOfTypeTen(BaseViewHolder baseHolder, int position) {
        FeaturedPhotosHolder holder = (FeaturedPhotosHolder) baseHolder;
        holder.setMovies(mData.getItem(position));
    }

    @Override
    protected void setViewOfTypeFive(BaseViewHolder baseHolder, int position) {
        PollHolder holder = (PollHolder) baseHolder;
        PollData poll = mData.getItem(position);

        holder.question.setText(poll.getQuestion());
        holder.answerContainer.removeAllViews();

        holder.answerContainer.setTag(poll.getId());
        holder.txtViewAll.setVisibility(View.VISIBLE);
        holder.txtViewAll.setOnClickListener(view -> GlobalUtils.navigateActivity(holder.itemView.getContext(), true, PollsActivity.class));
        boolean isAnswered = PollUtil.isAnswered(poll.getId());
        int answerIndex = PollUtil.getAnswered(poll.getId());

        List<Option> options = poll.getOptions();

        for (int i = 0; i < options.size(); i++) {
            Option option = options.get(i);
            RadioButton optionButton = new RadioButton((baseHolder.itemView.getContext()));
            optionButton.setId(ViewIdGenerator.generateViewId());
            optionButton.setTag(i);
            optionButton.setText(option.getText());
            holder.answerContainer.addView(optionButton);

            if (isAnswered && answerIndex == i) holder.answerContainer.check(optionButton.getId());
        }

        if (isAnswered) {
            holder.answerContainer.setEnabled(false);
            holder.submitButton.setEnabled(false);
        } else {
            holder.answerContainer.setEnabled(true);
            holder.submitButton.setEnabled(true);
        }
    }

    @Override
    protected void setViewOfTypeFour(BaseViewHolder baseHolder, int position) {
        FeaturedNewsListHolder holder = (FeaturedNewsListHolder) baseHolder;
        holder.setNews(mData.getItem(position));
        holder.txtViewAll.setVisibility(View.VISIBLE);
        holder.txtViewAll.setOnClickListener(view -> GlobalUtils.navigateActivity(holder.itemView.getContext(), true, NewsGossipsActivity.class));
        holder.title.setText("Hot News");
    }

    @Override
    protected void setViewOfTypeThree(BaseViewHolder baseHolder, int position) {
        FeaturedNewsHolder holder = (FeaturedNewsHolder) baseHolder;
        Article news = mData.getItem(position);
        holder.title.setText(news.getTitle());
    }

    @Override
    protected void setViewOfTypeTwo(BaseViewHolder baseHolder, int position) {
        UpcomingReleaseHolder holder = (UpcomingReleaseHolder) baseHolder;
        holder.setMovies(mData.getItem(position));
        holder.title.setText("Movies");
        holder.txtViewAll.setVisibility(View.VISIBLE);
        holder.txtViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalUtils.navigateActivity(holder.itemView.getContext(), false, MovieActivity.class);
            }
        });
    }

    @Override
    protected void setViewOfTypeOne(BaseViewHolder baseHolder, int position) {
        NewReleaseHolder holder = (NewReleaseHolder) baseHolder;
        holder.setMovies(mData.getItem(position));
        holder.title.setText("New Releases");
    }

    @Override
    protected void setViewOfTypeZero(BaseViewHolder baseHolder, int position) {
        String header = mData.getItem(position);
        HeaderHolder holder = (HeaderHolder) baseHolder;
        holder.header.setText(header);
    }

    @Override
    public int getItemViewType(int position) {
        return mData.getType(position);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.getItemCount();
    }


    public void setHomeData(HomeData data) {
        this.mData = HomeDataWrapper.wrap(data);
        Log.i(TAG, "Total items in home: " + mData.getItemCount());
        notifyDataSetChanged();
    }

    public void addMovies(List<Movie> movies) {
        if (this.mData != null) {
            int start = this.mData.addMovies(movies);
            notifyItemRangeInserted(start, movies.size());
        }
    }
}
